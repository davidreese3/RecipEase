package com.thesis.recipease.util.generator.recipe;

import com.thesis.recipease.util.generator.ErrorMessageGenerator;
import org.springframework.stereotype.Service;

@Service
public class VariationNotFoundErrorGenerator extends ErrorMessageGenerator {
    private static final String [] VARIATION_ERRORS = {
            "Oops! You can't remix a recipe that doesn't exist. Try starting from scratch!",
            "Uh-oh! This recipe is missing, so variations are off the table. Maybe cook up something new?",
            "Looks like the original recipe is missing—no variations allowed on thin air!",
            "This recipe has vanished, which means variations are a no-go. Try a different dish!",
            "Trying to customize a missing recipe? That's like seasoning a meal that isn't there!",
            "Recipe not found! Unfortunately, you can’t build on something that doesn’t exist."
    };

    protected VariationNotFoundErrorGenerator() {
        super(VARIATION_ERRORS);
    }
}
