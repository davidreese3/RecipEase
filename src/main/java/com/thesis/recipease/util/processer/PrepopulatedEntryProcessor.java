package com.thesis.recipease.util.processer;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.GlossaryEntry;
import com.thesis.recipease.model.SubstitutionEntry;
import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.recipe.RecipeDirection;
import com.thesis.recipease.model.recipe.RecipeIngredient;
import com.thesis.recipease.util.normalizer.GlossaryNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrepopulatedEntryProcessor {
    @Autowired
    private AppService appService;
    @Autowired
    private GlossaryNormalizer glossaryNormalizer;
    @Autowired
    private LemmaProcessor lemmaProcessor;

    StringBuilder glossaryTerms = new StringBuilder();
    StringBuilder substitutionTerms = new StringBuilder();

    public List<GlossaryEntry> ProcessGlossaryEntries(Recipe recipe){
        String glossaryTermsString =  glossaryTerms.toString().toLowerCase();

        glossaryTermsString = glossaryNormalizer.normalize(glossaryTermsString);
        String glossaryLemmasFinal = lemmaProcessor.processText(glossaryTermsString);

        // unit shouldn't go through lemma as it is a specific word
        String unit = recipe.getRecipeInfo().getUnitOfYield();
        glossaryLemmasFinal = glossaryLemmasFinal + " " + unit.toLowerCase();

        List<GlossaryEntry> glossaryEntries = appService.getAllGlossaryEntries();

        List<GlossaryEntry> glossaryList = new ArrayList<GlossaryEntry>();
        for(GlossaryEntry glossaryEntry : glossaryEntries){
            for(String relatedTerm: glossaryEntry.getRelatedTerms()){
                if(glossaryLemmasFinal.contains(relatedTerm.toLowerCase())){
                    glossaryList.add(glossaryEntry);
                    break;
                }
            }
        }
        return glossaryList;
    }

    public List<SubstitutionEntry> ProcessSubstitutionEntries(Recipe recipe){
        String substitutionTermsString =  substitutionTerms.toString().toLowerCase();

        String substitutionLemmasFinal = lemmaProcessor.processText(substitutionTermsString);

        List<SubstitutionEntry> substitutionEntries = appService.getAllSubstitutionEntries();
        List<SubstitutionEntry> substitutionList = new ArrayList<SubstitutionEntry>();

        for(SubstitutionEntry substitutionEntry : substitutionEntries) {
            if (substitutionLemmasFinal.contains(substitutionEntry.getOriginalComponent().toLowerCase())) {
                substitutionList.add(substitutionEntry);
            }
        }
        return substitutionList;
    }

    public void gatherStartingStrings(Recipe recipe){
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
    }
}
