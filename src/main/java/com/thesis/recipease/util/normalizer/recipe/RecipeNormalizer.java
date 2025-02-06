package com.thesis.recipease.util.normalizer.recipe;

import com.thesis.recipease.model.domain.recipe.Recipe;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import com.thesis.recipease.model.domain.recipe.RecipeIngredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeNormalizer {
    public Recipe normalizeRecipe(Recipe recipe){
        List<RecipeIngredient> recipeIngredients = recipe.getIngredients();
        for(RecipeIngredient recipeIngredient : recipeIngredients){
            recipeIngredient.setMeasurement(normalizeMeasurement(
                    recipeIngredient.getMeasurement(),
                    recipeIngredient.getWholeNumberQuantity(),
                    recipeIngredient.getFractionQuantity(),
                    recipeIngredient.getPreparation()));
        }
        recipe.setIngredients(recipeIngredients);
        RecipeInfo recipeInfo = recipe.getInfo();
        recipeInfo.setUnitOfYield(normalizeUnitOfYield(recipeInfo.getUnitOfYield(), recipeInfo.getYield()));
        recipe.setInfo(recipeInfo);
        return recipe;
    }

    public RecipeInfo normalizeRecipeInfo(RecipeInfo recipeInfo){
        recipeInfo.setDescription(normalizeDescription(recipeInfo.getDescription()));
        recipeInfo.setUnitOfYield(normalizeUnitOfYield(recipeInfo.getUnitOfYield(), recipeInfo.getYield()));
        return recipeInfo;
    }

    public String normalizeMeasurement(String measurement, Integer wholeNumberQuantity, String fractionQuantity, String preparation) {
        if (measurement != null && preparation != null
                && measurement.equals("Whole") && preparation.equals("Unprepared")) {
            return "";
        }
        if (measurement.equals("Fluid Ounces")){
            return "Fluid Ounce";
        }
        if (wholeNumberQuantity == 1 && fractionQuantity.equals("0")) {
            if (measurement.endsWith("es")) {
                return measurement.substring(0, measurement.length() - 2);
            } else if (measurement.endsWith("s")) {
                return measurement.substring(0, measurement.length() - 1);
            }
        }
        return measurement;
    }


    public String normalizeUnitOfYield(String unitOfYield, double yield) {
        if (yield != 1){
            return unitOfYield;
        }
        else{
            if(unitOfYield.equals("Finished Dishes")){
                return "Finished Dish";
            }
            else if(unitOfYield.equals("Patties")){
                return "Patty";
            }
            else if(unitOfYield.equals("Loaves")){
                return "Loaf";
            }
            else{
                unitOfYield = unitOfYield.substring(0,unitOfYield.length()-1);
            }
            return unitOfYield;
        }
    }

    public String normalizeDescription(String description){
        if (description.length() > 150) {
            description = description.substring(0, 150);
            description = description.substring(0, description.lastIndexOf(" ")) + "...";
        }
        return description;
    }
}
