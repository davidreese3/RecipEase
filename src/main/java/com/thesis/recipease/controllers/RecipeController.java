package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.WebRecipe;
import com.thesis.recipease.util.validator.RecipeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecipeController {
    @Autowired
    private RecipeValidator recipeValidator;
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model){
        WebRecipe webRecipe = new WebRecipe();
        model.addAttribute(webRecipe);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, WebRecipe webRecipe){
        String str = webRecipe.toString();
        return "recipe/createRecipe";
    }
}
