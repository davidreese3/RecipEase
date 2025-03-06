package com.thesis.recipease.util.sanitizer.recipe;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebUserSubstitutionEntry;
import com.thesis.recipease.util.sanitizer.Sanitizer;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserSubstitutionSanitizer implements Sanitizer<WebRecipe> {
    public WebRecipe sanitize(WebRecipe webRecipe){
        List<WebUserSubstitutionEntry> webUserSubstitutionEntries = webRecipe.getUserSubstitutionEntries();
        if(webUserSubstitutionEntries != null) {
            Iterator<WebUserSubstitutionEntry> userSubstitutionEntryIterator = webUserSubstitutionEntries.iterator();
            WebUserSubstitutionEntry webUserSubstitutionEntry;
            while (userSubstitutionEntryIterator.hasNext()) {
                webUserSubstitutionEntry = userSubstitutionEntryIterator.next();
                if (isUserSubNull(webUserSubstitutionEntry)) {
                    userSubstitutionEntryIterator.remove();
                }
            }
        }
        webRecipe.setUserSubstitutionEntries(webUserSubstitutionEntries);
        return webRecipe;
    }

    private boolean isUserSubNull(WebUserSubstitutionEntry webUserSubstitutionEntry){
        return webUserSubstitutionEntry.getOriginalComponent() == null &&
                webUserSubstitutionEntry.getOriginalQuantity() == null &&
                webUserSubstitutionEntry.getOriginalMeasurement() == null &&
                webUserSubstitutionEntry.getOriginalPreparation() == null &&
                webUserSubstitutionEntry.getSubstitutedComponent() == null &&
                webUserSubstitutionEntry.getSubstitutedQuantity() == null &&
                webUserSubstitutionEntry.getSubstitutedMeasurement() == null &&
                webUserSubstitutionEntry.getSubstitutedPreparation() == null;
    }
}
