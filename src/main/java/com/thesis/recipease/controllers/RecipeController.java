package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.glossary.GlossaryEntry;
import com.thesis.recipease.model.domain.recipe.*;
import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import com.thesis.recipease.model.web.recipe.*;
import com.thesis.recipease.model.web.recipe.util.WebRating;
import com.thesis.recipease.model.web.recipe.util.WebScaling;
import com.thesis.recipease.model.web.recipe.util.WebSearch;
import com.thesis.recipease.util.error.RecipeErrorMessageGenerator;
import com.thesis.recipease.util.normalizer.recipe.RecipeNormalizer;
import com.thesis.recipease.util.normalizer.substitution.SubstitutionNormalizer;
import com.thesis.recipease.util.processer.PrepopulatedEntryProcessor;
import com.thesis.recipease.util.sanitizer.IngredientSanitizer;
import com.thesis.recipease.util.sanitizer.RecipeSanitizer;
import com.thesis.recipease.util.scaler.IngredientScaler;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.storage.StorageService;
import com.thesis.recipease.util.validator.recipe.RecipeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.security.Principal;
import java.util.*;

@Controller
public class RecipeController {
    @Autowired
    private RecipeSanitizer recipeSanitizer;
    @Autowired
    private AppService appService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private PrepopulatedEntryProcessor prepopulatedEntryProcessor;
    @Autowired
    private SubstitutionNormalizer substitutionNormalizer;
    @Autowired
    private RecipeNormalizer recipeNormalizer;
    @Autowired
    private RecipeErrorMessageGenerator recipeErrorMessageGenerator;
    @Autowired
    private RecipeValidator recipeValidator;
    @Autowired
    IngredientScaler ingredientScaler;

    private final StorageService storageService;

    @Autowired
    public RecipeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model) {
        WebRecipe webRecipe = new WebRecipe();
        webRecipe.setVariation(new WebVariation(-1,true));
        model.addAttribute("webRecipe", webRecipe);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/variation/create", method = RequestMethod.GET)
    public String displayVariationCreationForm(Model model, @RequestParam("recipeId") int recipeId) {
        WebRecipe webRecipe = appService.getWebRecipeById(recipeId);
        if (webRecipe == null) {
            model.addAttribute("message", recipeErrorMessageGenerator.getVariationError());
            return "recipe/createVariationDNE";
        }

        String variationPattern = ".*\\[v\\d+\\.\\d+]$";
        WebInfo webInfo = webRecipe.getInfo();
        String name = webInfo.getName();
        if (name.matches(variationPattern)){
            name = name.replaceAll("\\[v\\d+\\.\\d+]$", "").trim();
            webInfo.setName(name);
            webRecipe.setInfo(webInfo);
        }
        webRecipe.setVariation(new WebVariation(recipeId,true));
        model.addAttribute("webRecipe", webRecipe);
        System.out.print("Variation: " +webRecipe.getVariation().isVariation()+ " [" + webRecipe.getVariation().getOriginalrecipeid() +"]" + "[" +recipeId+ "]");
        return "recipe/createVariation";
    }

    @RequestMapping(value = {"/recipe/create", "/recipe/variation/create"}, method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipe webRecipe, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        webRecipe = recipeSanitizer.sanitizeRecipe(webRecipe);
        if (!file.isEmpty()) {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')); // Get file extension
            webRecipe.setPhoto(new WebPhoto("recipe|" + extension, file));
        } else {
            webRecipe.setPhoto(null);
        }
        ArrayList<String> errors = new ArrayList<String>();
        errors = recipeValidator.isRecipeValid(model, webRecipe);
        if (!errors.isEmpty()) {
            model.addAttribute(webRecipe);
            model.addAttribute("errors", errors);
            return "recipe/createRecipe";
        }
        WebVariation webVariation = webRecipe.getVariation();
        if (webVariation != null){
            int numVariation = appService.getNumberOfVariationByOriginalRecipeId(webVariation.getOriginalrecipeid());
            int numDepth = appService.getDepthOfVariationByOriginalRecipeId(webVariation.getOriginalrecipeid());
            WebInfo webInfo  = webRecipe.getInfo();
            webInfo.setName(webInfo.getName() + " [v" + numDepth + "." + numVariation + "]");
        }
        Recipe recipe = appService.addRecipe(securityService.getLoggedInUserId(), webRecipe);
        if (recipe == null){
            errors.add("There was an issue saving your recipe. Please try again");
            model.addAttribute(webRecipe);
            model.addAttribute("errors", errors);
            return "recipe/createRecipe";
        }
        else {
            if (!file.isEmpty()) {
                storageService.store(file, recipe.getRecipeInfo().getRecipeId());
            }
            redirectAttributes.addFlashAttribute("message", "Your recipe has been posted!");
            return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
        }
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId,
                                @RequestParam(value = "scale", required = false) Double scale) {
        Recipe recipe = appService.getRecipeById(recipeId);
        if (recipe == null){
            model.addAttribute("message", recipeErrorMessageGenerator.getRecipeError());
            return "recipe/viewRecipeDNE";
        }
        if (scale != null){
            if (scale != 1) {
                List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
                recipe.setRecipeIngredients(ingredientScaler.Scale(scale, ingredients));
            }
        }
        recipe = recipeNormalizer.normalizeRecipe(recipe);
        //insert scaling logic here
        model.addAttribute("recipe", recipe);

        String authorName = appService.getNameById(recipe.getRecipeInfo().getUserId());
        model.addAttribute("authorName", authorName);

        prepopulatedEntryProcessor.gatherStartingStrings(recipe);
        List<GlossaryEntry> glossaryList = prepopulatedEntryProcessor.ProcessGlossaryEntries(recipe);
        model.addAttribute("glossaryList", glossaryList);

        List<SubstitutionEntry> substitutionList = prepopulatedEntryProcessor.ProcessSubstitutionEntries(recipe);
        substitutionList = substitutionNormalizer.normalizeSubs(substitutionList);
        model.addAttribute("substitutionList", substitutionList);

        List<RecipeUserSubstitutionEntry> userSubs = recipe.getUserSubstitutionEntries();
        userSubs = substitutionNormalizer.normalizeUserSubs(userSubs);
        recipe.setUserSubstitutionEntries(userSubs);

        RecipeVariation recipeVariation = recipe.getRecipeVariation();
        if(recipeVariation  != null) {
            model.addAttribute("originalLink", "/recipe/view?recipeId=" + recipe.getRecipeVariation().getOriginalRecipeId());
        }

        RecipePhoto recipePhoto = recipe.getRecipePhoto();
        Path photoPath = null;
        if (recipePhoto != null) {
            String fileLocation = "/recipePictures/" + recipePhoto.getFileName();
            recipePhoto.setFileLocation(fileLocation); // Use public URL for display
        }

        boolean tagsExist = !(recipe.getRecipeTags().values().stream().allMatch(String::isEmpty));
        model.addAttribute("tagsExist", tagsExist);

        WebScaling webScaling = new WebScaling();
        webScaling.setRecipeId(recipeId);
        model.addAttribute("webScaling", webScaling);

        WebComment webComment = new WebComment();
        webComment.setRecipeId(recipeId);
        model.addAttribute("webComment", webComment);

        WebRating webRating = new WebRating();
        webRating.setRecipeId(recipeId);
        model.addAttribute("webRating", webRating);

        return "recipe/viewRecipe";
    }

    @RequestMapping(value = "/recipe/comment/add", method = RequestMethod.POST)
    public String processCommentForm(Model model, WebComment webComment, RedirectAttributes redirectAttributes){
        RecipeComment recipeComment = appService.addComment(securityService.getLoggedInUserId(), webComment.getRecipeId(), webComment);
        if(recipeComment != null){
            redirectAttributes.addFlashAttribute("message", "Your comment (\'"+webComment.getCommentText()+"\') has be posted");
        }
        return "redirect:/recipe/view?recipeId=" + webComment.getRecipeId();
    }

    @RequestMapping(value = "/recipe/rating/add", method = RequestMethod.POST)
    public String processRatingForm(Model model, WebRating webRating, RedirectAttributes redirectAttributes){
        if (securityService.getLoggedInUserId() == appService.getUserIdByRecipeId(webRating.getRecipeId())){
            redirectAttributes.addFlashAttribute("error", "You cannot rate your own recipe");
            return "redirect:/recipe/view?recipeId=" + webRating.getRecipeId();

        }
        RecipeRating recipeRating = appService.addRating(securityService.getLoggedInUserId(), webRating.getRecipeId(), webRating);
        if (recipeRating != null) {
            redirectAttributes.addFlashAttribute("message", "Your rating ("+webRating.getRatingValue()+") has be added");
        }
        return "redirect:/recipe/view?recipeId=" + webRating.getRecipeId();
    }

    @RequestMapping(value = "/recipe/scaling", method = RequestMethod.POST)
    public String processScalingForm(Model model, WebScaling webScaling, RedirectAttributes redirectAttributes) {
        return "redirect:/recipe/view?recipeId=" + webScaling.getRecipeId() + "&scale=" + webScaling.getScalingValue();
    }

    @RequestMapping(value = "/recipe/search", method = RequestMethod.GET)
    public String displaySearchForm(Model model, WebSearch webSearch){
        webSearch = new WebSearch();
        model.addAttribute("webSearch", webSearch);
        return "recipe/searchRecipe";
    }

    @RequestMapping(value = "/recipe/search", method = RequestMethod.POST)
    public String processSearchForm(Model model, WebSearch webSearch){
        String str = webSearch.getName();
        List<RecipeInfo> results = appService.getRecipesBySearchCriteria(webSearch);
        webSearch.setName(str);
        model.addAttribute("webSearch", webSearch);
        model.addAttribute("results",results);
        return "recipe/searchRecipe";
    }
}
