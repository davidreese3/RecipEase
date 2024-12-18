package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.util.validator.RecipeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RecipeController {
    @Autowired
    private RecipeValidator recipeValidator;
    @Autowired
    private AppService appService;


}
