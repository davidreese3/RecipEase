package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.Recipe;
import com.thesis.recipease.model.web.WebRecipeInfo;
import com.thesis.recipease.util.validator.RecipeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class RecipeController {
    @Autowired
    private RecipeValidator recipeValidator;
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/recipe/create", method = RequestMethod.GET)
    public String displayRecipeCreationForm(Model model){
        WebRecipeInfo webRecipeInfo = new WebRecipeInfo();
        model.addAttribute(webRecipeInfo);
        return "recipe/createRecipe";
    }

    @RequestMapping(value = "/recipe/create", method = RequestMethod.POST)
    public String processRecipeCreationForm(Model model, Principal principal, WebRecipeInfo webRecipeInfo){
        if (webRecipeInfo.getPrepHr() == null) webRecipeInfo.setPrepHr(0);
        if (webRecipeInfo.getPrepMin() == null) webRecipeInfo.setPrepMin(0);
        if (webRecipeInfo.getProcessHr() == null) webRecipeInfo.setProcessHr(0);
        if (webRecipeInfo.getProcessMin() == null) webRecipeInfo.setProcessMin(0);
        if (webRecipeInfo.getYield() == null) webRecipeInfo.setYield(1.0);
        //validator
        String str = webRecipeInfo.toString();
        Recipe recipe = appService.addRecipe(principal.getName(), webRecipeInfo);
        System.out.println("[Recipe From DB in String]: " + recipe.toString());
        return "recipe/createRecipe";
    }
}
