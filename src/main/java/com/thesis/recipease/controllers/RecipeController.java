package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.sanitizer.RecipeSanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class RecipeController {
    //@Autowired
    //private RecipeSanitizer recipeSanitizer;
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model){
        WebRecipe webRecipe = new WebRecipe();
        model.addAttribute("webRecipe", webRecipe);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipe webRecipe, RedirectAttributes redirectAttributes){
        System.out.println("[Before Sanitizer]: " + webRecipe.toString());
        webRecipe = RecipeSanitizer.sanitizeRecipe(webRecipe);
        System.out.println("[After Sanitizer]: " + webRecipe.toString());
        //needs to get fixed
        Recipe recipe = appService.addRecipe(appService.getLoggedInUserId(), webRecipe);
        //redirectAttributes.addFlashAttribute("recipe", recipe);
        redirectAttributes.addFlashAttribute("message", "Your recipe has been posted!");
        return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId){
        if (!model.containsAttribute("recipe")) {
            Recipe recipe = appService.getRecipeById(recipeId);
            model.addAttribute("recipe", recipe);
        }
        return "recipe/viewRecipe";
    }
}
