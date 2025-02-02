package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.glossary.GlossaryEntry;
import com.thesis.recipease.model.domain.recipe.*;
import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import com.thesis.recipease.model.web.recipe.*;
import com.thesis.recipease.util.error.RecipeErrorMessageGenerator;
import com.thesis.recipease.util.normalizer.RecipeNormalizer;
import com.thesis.recipease.util.normalizer.SubstitutionNormalizer;
import com.thesis.recipease.util.processer.PrepopulatedEntryProcessor;
import com.thesis.recipease.util.sanitizer.RecipeSanitizer;
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

    private final StorageService storageService;

    @Autowired
    public RecipeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model) {
        WebRecipe webRecipe = new WebRecipe();
        model.addAttribute("webRecipe", webRecipe);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipe webRecipe, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        webRecipe = recipeSanitizer.sanitizeRecipe(webRecipe);
        if (!file.isEmpty()) {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')); // Get file extension
            webRecipe.setPhoto(new WebPhoto("recipe|" + extension));
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
        Recipe recipe = appService.addRecipe(securityService.getLoggedInUserId(), webRecipe);
        if (!file.isEmpty()) {
            storageService.store(file, recipe.getRecipeInfo().getRecipeId());
        }
        redirectAttributes.addFlashAttribute("message", "Your recipe has been posted!");
        return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId) {
        Recipe recipe = appService.getRecipeById(recipeId);
        if (recipe == null){
            model.addAttribute("message", recipeErrorMessageGenerator.getRecipeError());
            return "recipe/viewRecipeDNE";
        }
        // reason for this is so that when versioning it can select the correct type from db
        recipe = recipeNormalizer.normalizeRecipe(recipe);
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

        RecipePhoto recipePhoto = recipe.getRecipePhoto();
        Path photoPath = null;
        if (recipePhoto != null) {
            String fileLocation = "/recipePictures/" + recipePhoto.getFileName();
            recipePhoto.setFileLocation(fileLocation); // Use public URL for display
        }

        boolean tagsExist = !(recipe.getRecipeTags().values().stream().allMatch(String::isEmpty));
        model.addAttribute("tagsExist", tagsExist);

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
        RecipeRating recipeRating = appService.addRating(securityService.getLoggedInUserId(), webRating.getRecipeId(), webRating);
        if (recipeRating != null) {
            redirectAttributes.addFlashAttribute("message", "Your rating ("+webRating.getRatingValue()+") has be added");
        }
        return "redirect:/recipe/view?recipeId=" + webRating.getRecipeId();
    }
}
