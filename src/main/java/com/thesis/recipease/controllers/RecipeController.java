package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.GlossaryEntry;
import com.thesis.recipease.model.SubstitutionEntry;
import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.normalizer.SubstitutionNormalizer;
import com.thesis.recipease.util.processer.PrepopulatedEntryProcessor;
import com.thesis.recipease.util.sanitizer.RecipeSanitizer;
import com.thesis.recipease.util.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.*;

@Controller
public class RecipeController {
    //@Autowired
    //private RecipeSanitizer recipeSanitizer;
    @Autowired
    private AppService appService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private PrepopulatedEntryProcessor prepopulatedEntryProcessor;
    @Autowired
    private SubstitutionNormalizer substitutionNormalizer;

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model){
        WebRecipe webRecipe = new WebRecipe();
        model.addAttribute("webRecipe", webRecipe);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipe webRecipe, RedirectAttributes redirectAttributes){
        System.out.println("[Before]");
        webRecipe = RecipeSanitizer.sanitizeRecipe(webRecipe);
        Recipe recipe = appService.addRecipe(securityService.getLoggedInUserId(), webRecipe);
        redirectAttributes.addFlashAttribute("message", "Your recipe has been posted!");
        return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId){
        Recipe recipe = appService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);

        prepopulatedEntryProcessor.gatherStartingStrings(recipe);
        List<GlossaryEntry> glossaryList = prepopulatedEntryProcessor.ProcessGlossaryEntries(recipe);

        model.addAttribute("glossaryList", glossaryList);

        List<SubstitutionEntry> substitutionList = prepopulatedEntryProcessor.ProcessSubstitutionEntries(recipe);
        substitutionList = substitutionNormalizer.normalize(substitutionList);
        model.addAttribute("substitutionList", substitutionList);

        int userId = recipe.getRecipeInfo().getUserId();
        String authorName = appService.getNameById(userId);
        model.addAttribute("authorName", authorName);
        model.addAttribute("profileLink", "http://localhost:8080/profile/view?id=" + userId);
        return "recipe/viewRecipe";
    }

}
