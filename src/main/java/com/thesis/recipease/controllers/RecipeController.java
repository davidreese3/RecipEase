package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.GlossaryEntry;
import com.thesis.recipease.model.SubstitutionEntry;
import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.recipe.RecipePhoto;
import com.thesis.recipease.model.recipe.RecipeUserSubstitutionEntry;
import com.thesis.recipease.model.web.recipe.WebPhoto;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebUserSubstitutionEntry;
import com.thesis.recipease.util.normalizer.RecipeNormalizer;
import com.thesis.recipease.util.normalizer.SubstitutionNormalizer;
import com.thesis.recipease.util.processer.PrepopulatedEntryProcessor;
import com.thesis.recipease.util.sanitizer.RecipeSanitizer;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.storage.StorageService;
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

    private final StorageService storageService;

    @Autowired
    public RecipeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model){
        WebRecipe webRecipe = new WebRecipe();
        model.addAttribute("webRecipe", webRecipe);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipe webRecipe, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        webRecipe = recipeSanitizer.sanitizeRecipe(webRecipe);
        if(!file.isEmpty()) {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')); // Get file extension
            webRecipe.setPhoto(new WebPhoto("recipe|"+extension));
        }
        else{
            webRecipe.setPhoto(null);
        }
        Recipe recipe = appService.addRecipe(securityService.getLoggedInUserId(), webRecipe);
        if(!file.isEmpty()) {
            storageService.store(file, recipe.getRecipeInfo().getRecipeId());
        }
        redirectAttributes.addFlashAttribute("message", "Your recipe has been posted!");
        return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId){
        Recipe recipe = appService.getRecipeById(recipeId);
        // reason for this is so that when versioning it can select the correct type from db
        recipe = recipeNormalizer.normalizeRecipe(recipe);
        boolean tagsExist = !(recipe.getRecipeTags().values().stream().allMatch(String::isEmpty));
        model.addAttribute("recipe", recipe);
        model.addAttribute("tagsExist", tagsExist);

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

        int userId = recipe.getRecipeInfo().getUserId();
        String authorName = appService.getNameById(userId);
        model.addAttribute("authorName", authorName);
        model.addAttribute("profileLink", "http://localhost:8080/profile/view?id=" + userId);

        return "recipe/viewRecipe";
    }
}
