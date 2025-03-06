package com.thesis.recipease.util.scaler;

import com.thesis.recipease.model.domain.recipe.RecipeIngredient;
import com.thesis.recipease.util.sanitizer.recipe.util.QuantitySanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientScaler {
    @Autowired
    QuantitySanitizer quantitySanitizer;

    public List<RecipeIngredient> Scale(double scaleFactor, List<RecipeIngredient> ingredientList){
        double quantity;
        for(RecipeIngredient ingredient : ingredientList){
            quantity = parse(ingredient.getQuantity());
            quantity = quantity * scaleFactor;
            String quantityStr = "" + quantity;
            quantityStr = quantitySanitizer.sanitize(quantityStr);
            ingredient.setQuantity(quantityStr);
        }
        return ingredientList;
    }

    private double parse(String quantity){
        double val = 0;
        if (quantity.contains(" ")) {
            String[] parts = quantity.split(" ");
            val = Double.parseDouble(parts[0]);

            if (parts[1].contains("/")) {
                String[] fraction = parts[1].split("/");
                double numerator = Double.parseDouble(fraction[0]);
                double denominator = Double.parseDouble(fraction[1]);
                val += numerator / denominator;
            }
        }
        else if (quantity.contains("/")) {
            String[] fractionParts = quantity.split("/");
            double numerator = Double.parseDouble(fractionParts[0]);
            double denominator = Double.parseDouble(fractionParts[1]);
            val = numerator / denominator;
        }
        else {
            val = Double.parseDouble(quantity);
        }
        return val;
    }
}
