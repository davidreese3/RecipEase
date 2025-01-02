package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.GlossaryEntry;
import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.recipe.RecipeDirection;
import com.thesis.recipease.model.recipe.RecipeIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.normalizer.GlossaryNormalizer;
import com.thesis.recipease.util.processer.LemmaProcessor;
import com.thesis.recipease.util.sanitizer.RecipeSanitizer;
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
    private GlossaryNormalizer glossaryNormalizer;
    @Autowired
    private LemmaProcessor lemmaProcessor;

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
        redirectAttributes.addFlashAttribute("message", "Your recipe has been posted!");
        return "redirect:/recipe/view?recipeId=" + recipe.getRecipeInfo().getRecipeId();
    }

    @RequestMapping(value = "/recipe/view", method = RequestMethod.GET)
    public String displayRecipe(Model model, @RequestParam("recipeId") int recipeId){
        Recipe recipe = appService.getRecipeById(recipeId);
        model.addAttribute("recipe", recipe);

        StringBuilder glossaryTerms = new StringBuilder();
        StringBuilder substitutionTerms = new StringBuilder();
        String unit = recipe.getRecipeInfo().getUnitOfYield();

        List<RecipeIngredient> recipeIngredientList = recipe.getRecipeIngredients();
        for(RecipeIngredient recipeIngredient : recipeIngredientList){
            glossaryTerms.append(recipeIngredient.getPreparation()).append(' ');
            glossaryTerms.append(recipeIngredient.getMeasurement()).append(' ');
            substitutionTerms.append(recipeIngredient.getComponent()).append(' ');
        }

        List<RecipeDirection> recipeDirectionList = recipe.getRecipeDirections();
        for(RecipeDirection recipeDirection : recipeDirectionList){
            glossaryTerms.append(recipeDirection.getDirection()).append(' ');
            glossaryTerms.append(recipeDirection.getMethod()).append(' ');
        }

        String glossaryTermsString =  glossaryTerms.toString().toLowerCase();

        //normalize and extract lemmas
        glossaryTermsString = glossaryNormalizer.normalize(glossaryTermsString);
        String glossaryLemmasFinal = lemmaProcessor.processText(glossaryTermsString);

        // unit shouldn't go through lemma as it is a specific word
        glossaryLemmasFinal = glossaryLemmasFinal + " " + unit.toLowerCase();
        System.out.println("[Finished G]: " + glossaryLemmasFinal);



        String substitutionTermsString =  substitutionTerms.toString().toLowerCase();

        //extract lemmas
        String substitutionLemmasFinal = lemmaProcessor.processText(substitutionTermsString);

        System.out.println("[Finished S]: " + substitutionLemmasFinal);

        List<GlossaryEntry> glossaryEntries = appService.getAllGlossaryEntries();
        System.out.print(glossaryEntries.get(0).toString());
        System.out.print(glossaryEntries.get(1).toString());
        System.out.print(glossaryEntries.get(2).toString());

        List<GlossaryEntry> glossaryList = new ArrayList<GlossaryEntry>();
        for(GlossaryEntry glossaryEntry : glossaryEntries){
            for(String relatedTerm: glossaryEntry.getRelatedTerms()){
                if(glossaryLemmasFinal.contains(relatedTerm.toLowerCase())){
                    glossaryList.add(glossaryEntry);
                    break;
                }
            }
        }
        for(GlossaryEntry glossaryEntry : glossaryList){
            System.out.println("To Attatch: " + glossaryEntry.getTerm() + " " + glossaryEntry.getDefinition());
        }
        //anywayto take out if is an ingredient?
        model.addAttribute("glossaryList", glossaryList);
        return "recipe/viewRecipe";
    }

}
