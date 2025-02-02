package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebUserSubstitutionEntry;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserSubstitutionSanitizer implements Sanitizer{
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
                else {
                    if (webUserSubstitutionEntry.getOriginalWholeNumberQuantity() == null) {
                        webUserSubstitutionEntry.setOriginalWholeNumberQuantity(0);
                    }
                    if (webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity() == null) {
                        webUserSubstitutionEntry.setSubstitutedWholeNumberQuantity(0);
                    }
                }
            }
        }
        webRecipe.setUserSubstitutionEntries(webUserSubstitutionEntries);
        return webRecipe;
    }

    private boolean isUserSubNull(WebUserSubstitutionEntry webUserSubstitutionEntry){
        return webUserSubstitutionEntry.getOriginalComponent() == null &&
                webUserSubstitutionEntry.getOriginalWholeNumberQuantity() == null &&
                webUserSubstitutionEntry.getOriginalFractionQuantity() == null &&
                webUserSubstitutionEntry.getOriginalMeasurement() == null &&
                webUserSubstitutionEntry.getOriginalPreparation() == null &&
                webUserSubstitutionEntry.getSubstitutedComponent() == null &&
                webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity() == null &&
                webUserSubstitutionEntry.getSubstitutedFractionQuantity() == null &&
                webUserSubstitutionEntry.getSubstitutedMeasurement() == null &&
                webUserSubstitutionEntry.getSubstitutedPreparation() == null;
    }
}
