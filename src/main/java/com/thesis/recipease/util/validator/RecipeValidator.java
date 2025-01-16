package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class RecipeValidator {

    public RecipeValidator() {}

    public boolean isRecipeValid(Model model, WebRecipe webRecipe) {
        if (!isNameValid(webRecipe.getInfo().getName())) {
            model.addAttribute("error", "Recipe name is required and cannot exceed 40 characters.");
            return false;
        }

        if (!isDescriptionValid(webRecipe.getInfo().getDescription())) {
            model.addAttribute("error", "Description cannot exceed 1000 characters.");
            return false;
        }

        if (!isYieldValid(webRecipe.getInfo().getYield())) {
            model.addAttribute("error", "Yield must be greater than 0.");
            return false;
        }

        if (!isUnitOfYieldValid(webRecipe.getInfo().getUnitOfYield())) {
            model.addAttribute("error", "Unit of yield is required.");
            return false;
        }

        if (!areIngredientsValid(webRecipe.getIngredients())) {
            model.addAttribute("error", "Each ingredient must have a valid name, measurement, and preparation. Names cannot exceed 45 characters.");
            return false;
        }

        if (!areDirectionsValid(webRecipe.getDirections())) {
            model.addAttribute("error", "Each direction must have a valid description and cannot exceed 300 characters.");
            return false;
        }

        return true;
    }

    private boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 40;
    }

    private boolean isDescriptionValid(String description) {
        return description == null || description.length() <= 1000;
    }

    private boolean isYieldValid(Double yield) {
        return yield != null && yield > 0;
    }

    private boolean isUnitOfYieldValid(String unitOfYield) {
        return unitOfYield != null && !unitOfYield.trim().isEmpty();
    }

    private boolean areIngredientsValid(List<WebIngredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return false;
        }

        for (WebIngredient ingredient : ingredients) {
            if (ingredient.getComponent() == null || ingredient.getComponent().trim().isEmpty() ||
                    ingredient.getComponent().length() > 45 ||
                    ingredient.getMeasurement() == null || ingredient.getMeasurement().trim().isEmpty() ||
                    ingredient.getPreparation() == null || ingredient.getPreparation().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean areDirectionsValid(List<WebDirection> directions) {
        if (directions == null || directions.isEmpty()) {
            return false;
        }

        for (WebDirection direction : directions) {
            if (direction.getDirection() == null || direction.getDirection().trim().isEmpty() ||
                    direction.getDirection().length() > 300) {
                return false;
            }
        }
        return true;
    }
}
