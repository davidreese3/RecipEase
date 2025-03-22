package com.thesis.recipease.web;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.glossary.GlossaryEntry;
import com.thesis.recipease.model.domain.recipe.*;
import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import com.thesis.recipease.model.domain.recipe.util.RecipeComment;
import com.thesis.recipease.model.domain.recipe.util.RecipeRating;
import com.thesis.recipease.model.domain.recipe.util.RecipeVariation;
import com.thesis.recipease.model.web.recipe.*;
import com.thesis.recipease.model.web.recipe.util.*;
import com.thesis.recipease.util.generator.recipe.RecipeNotFoundErrorGenerator;
import com.thesis.recipease.util.generator.recipe.VariationNotFoundErrorGenerator;
import com.thesis.recipease.util.normalizer.recipe.RecipeNormalizer;
import com.thesis.recipease.util.normalizer.substitution.SubstitutionNormalizer;
import com.thesis.recipease.util.processer.PrepopulatedEntryProcessor;
import com.thesis.recipease.util.sanitizer.recipe.util.CommentSanitizer;
import com.thesis.recipease.util.sanitizer.recipe.RecipeSanitizer;
import com.thesis.recipease.util.sanitizer.recipe.util.SearchSanitizer;
import com.thesis.recipease.util.scaler.IngredientScaler;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.storage.StorageService;
import com.thesis.recipease.util.validator.recipe.util.CommentValidator;
import com.thesis.recipease.util.validator.recipe.RecipeValidator;
import com.thesis.recipease.util.validator.recipe.util.SearchValidator;
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
    private RecipeValidator recipeValidator;
    @Autowired
    private CommentSanitizer commentSanitizer;
    @Autowired
    private CommentValidator commentValidator;
    @Autowired
    private IngredientScaler ingredientScaler;
    @Autowired
    private SearchSanitizer searchSanitizer;
    @Autowired
    private SearchValidator searchValidator;
    @Autowired
    private RecipeNotFoundErrorGenerator recipeNotFoundErrorGenerator;
    @Autowired
    private VariationNotFoundErrorGenerator variationNotFoundErrorGenerator;

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
            model.addAttribute("message", variationNotFoundErrorGenerator.getError());
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
        return "recipe/createVariation";
    }

    @RequestMapping(value = {"/recipe/create", "/recipe/variation/create"}, method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipe webRecipe, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        webRecipe = recipeSanitizer.sanitize(webRecipe);
        if (!file.isEmpty()) {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')); // Get file extension
            webRecipe.setPhoto(new WebPhoto("recipe|" + extension, file));
        } else {
            webRecipe.setPhoto(null);
        }
        ArrayList<String> errors = new ArrayList<String>();
        errors = recipeValidator.validate(webRecipe);
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
            errors.add("There was an issue saving your recipe. Please try again.");
            model.addAttribute(webRecipe);
            model.addAttribute("errors", errors);
            return "recipe/createRecipe";
        }
        if (!file.isEmpty()) {
            storageService.store(file, recipe.getRecipeInfo().getRecipeId());
        }
        redirectAttributes.addFlashAttribute("message", "Your recipe has been posted.");
        return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId,
                                @RequestParam(value = "scale", required = false) Double scale) {
        Recipe recipe = appService.getRecipeById(recipeId);
        if (recipe == null){
            model.addAttribute("message", recipeNotFoundErrorGenerator.getError());
            return "recipe/viewRecipeDNE";
        }
        if (scale != null){
            if (scale != 1) {
                List<RecipeIngredient> ingredients = recipe.getRecipeIngredients();
                recipe.setRecipeIngredients(ingredientScaler.Scale(scale, ingredients));
            }
        }
        recipe = recipeNormalizer.normalize(recipe);
        model.addAttribute("recipe", recipe);

        String authorName = appService.getNameById(recipe.getRecipeInfo().getUserId());
        model.addAttribute("authorName", authorName);

        prepopulatedEntryProcessor.gatherStartingStrings(recipe);
        List<GlossaryEntry> glossaryList = prepopulatedEntryProcessor.ProcessGlossaryEntries(recipe);
        model.addAttribute("glossaryList", glossaryList);

        List<SubstitutionEntry> substitutionList = prepopulatedEntryProcessor.ProcessSubstitutionEntries(recipe);
        substitutionList = substitutionNormalizer.normalize(substitutionList);
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
        boolean tagsExist = false;
        for (Map.Entry<String, String> entry : recipe.getRecipeTags().entrySet()) {
            if (!entry.getValue().isEmpty()) {
                tagsExist = true;
                break;
            }
        }

        model.addAttribute("tagsExist", tagsExist);

        boolean bookmarkExist = false;
        if(recipe.getRecipeBookmark() != null){
            bookmarkExist = true;
        }
        model.addAttribute("bookmarkExist", bookmarkExist);

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
        webComment = commentSanitizer.sanitize(webComment);
        ArrayList<String> errors = commentValidator.validate(webComment);
        if (errors.size() > 0) {
            redirectAttributes.addFlashAttribute("webComment",webComment);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/recipe/view?recipeId=" + webComment.getRecipeId();
        }
        RecipeComment recipeComment = appService.addComment(securityService.getLoggedInUserId(), webComment.getRecipeId(), webComment);
        if(recipeComment != null){
            redirectAttributes.addFlashAttribute("message", "Your comment (\'"+webComment.getCommentText()+"\') has be posted.");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "There was an issue saving your comment. Please try again.");
        }
        return "redirect:/recipe/view?recipeId=" + webComment.getRecipeId();
    }

    @RequestMapping(value = "/recipe/rating/add", method = RequestMethod.POST)
    public String processRatingForm(Model model, WebRating webRating, RedirectAttributes redirectAttributes){
        if (securityService.getLoggedInUserId() == appService.getUserIdByRecipeId(webRating.getRecipeId())){
            redirectAttributes.addFlashAttribute("error", "You cannot rate your own recipe.");
            return "redirect:/recipe/view?recipeId=" + webRating.getRecipeId();

        }
        RecipeRating recipeRating = appService.addRating(securityService.getLoggedInUserId(), webRating.getRecipeId(), webRating);
        if (recipeRating != null) {
            redirectAttributes.addFlashAttribute("message", "Your rating ("+webRating.getRatingValue()+") has be added.");
        }
        else{
            redirectAttributes.addFlashAttribute("error", "There was an issue saving your rating. Please try again.");
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
        webSearch = searchSanitizer.sanitize(webSearch);
        List<String> errors = searchValidator.validate(webSearch);
        if (!errors.isEmpty()) {
            model.addAttribute(webSearch);
            model.addAttribute("errors", errors);
            return "recipe/searchRecipe";
        }
        String str = webSearch.getName();
        List<RecipeInfo> results = appService.getRecipesBySearchCriteria(webSearch);
        webSearch.setName(str);
        model.addAttribute("webSearch", webSearch);
        if(results.isEmpty()){
            model.addAttribute("message", "There are no search results with your search criteria.");
            return "recipe/searchRecipe";
        }
        model.addAttribute("results", results);
        return "recipe/searchRecipe";
    }

    @RequestMapping(value = "/recipe/trending", method = RequestMethod.GET)
    public String displayTrendingRecipes(Model model){
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<String> errors = new ArrayList<>();
        List<RecipeInfo> communityRecipes = appService.getCommunityTrendingRecipes();
        model.addAttribute("communityRecipes",communityRecipes);
        if(communityRecipes == null){
            errors.add("There was an issue getting trending community recipes. Please try again.");
        }
        if(communityRecipes.size() == 0){
            messages.add("There are no current trending community recipes. Please check back later.");
        }
        List<RecipeInfo> staffRecipes = appService.getStaffTrendingRecipes();
        model.addAttribute("staffRecipes",staffRecipes);
        if(staffRecipes == null){
            errors.add("There was an issue getting trending staff recipes. Please try again.");
        }
        if(staffRecipes.size() == 0){
            messages.add("There are no current trending staff recipes. Please check back later.");
        }
        if (messages.size() != 0){
            model.addAttribute("messages",messages);
        }
        if (errors.size() != 0){
            model.addAttribute("errors",errors);
        }
        return "recipe/trendingRecipes";
    }

    @RequestMapping(value = "/recipe/bookmark/add", method = RequestMethod.POST)
    public String processBookmarkAddForm(Model model, @RequestParam("recipeId") int recipeId, RedirectAttributes redirectAttributes){
        int userId = securityService.getLoggedInUserId();
        if (appService.addRecipeToBookmark(userId,recipeId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Recipe could not be added to bookmark. Please try again.");
            return "redirect:/recipe/view?recipeId=" + recipeId;
        }
        redirectAttributes.addFlashAttribute("message", "This recipe was added to your bookmark.");

        return "redirect:/recipe/view?recipeId=" + recipeId;
    }

    @RequestMapping(value = "/recipe/bookmark/remove", method = RequestMethod.POST)
    public String processBookmarkRemoveForm(Model model, @RequestParam("recipeId") int recipeId, RedirectAttributes redirectAttributes){
        int userId = securityService.getLoggedInUserId();
        if (appService.deleteRecipeFromBookmark(userId,recipeId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Recipe could not be removed from bookmark. Please try again.");
            return "redirect:/recipe/view?recipeId=" + recipeId;
        }
        redirectAttributes.addFlashAttribute("message", "This recipe was remove from your bookmark.");

        return "redirect:/recipe/view?recipeId=" + recipeId;
    }

    @RequestMapping(value = "/recipe/bookmark/list/remove", method = RequestMethod.POST)
    public String processBookmarkRemoveFromProfileForm(Model model, @RequestParam("recipeId") int recipeId, RedirectAttributes redirectAttributes){
        int userId = securityService.getLoggedInUserId();
        if (appService.deleteRecipeFromBookmark(userId,recipeId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Recipe could not be removed from bookmark. Please try again.");
            return "redirect:/profile/view/personal";
        }
        redirectAttributes.addFlashAttribute("message", "This recipe was removed from your bookmark.");

        return "redirect:/profile/view/personal";
    }
}
