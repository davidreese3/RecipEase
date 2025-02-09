package com.thesis.recipease.util.normalizer.substitution;

import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import com.thesis.recipease.model.domain.recipe.RecipeUserSubstitutionEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubstitutionNormalizer {
    public List<SubstitutionEntry> normalizeSubs(List<SubstitutionEntry> substitutionList){
        for (SubstitutionEntry substitution : substitutionList) {
            String fixedOriginal = normalizeMeasurement(
                    substitution.getOriginalMeasurement(),
                    substitution.getOriginalQuantity()
            );
            String fixedSubstituted = normalizeMeasurement(
                    substitution.getSubstitutedMeasurement(),
                    substitution.getSubstitutedQuantity()
            );
            substitution.setOriginalMeasurement(fixedOriginal);
            substitution.setSubstitutedMeasurement(fixedSubstituted);
        }
        return substitutionList;
    }

    public List<RecipeUserSubstitutionEntry> normalizeUserSubs(List<RecipeUserSubstitutionEntry> substitutionList){
        for (RecipeUserSubstitutionEntry substitution : substitutionList) {
            String fixedOriginal = normalizeMeasurement(
                    substitution.getOriginalMeasurement(),
                    substitution.getOriginalQuantity()
            );
            String fixedSubstituted = normalizeMeasurement(
                    substitution.getSubstitutedMeasurement(),
                    substitution.getSubstitutedQuantity()
            );
            substitution.setOriginalMeasurement(fixedOriginal);
            substitution.setSubstitutedMeasurement(fixedSubstituted);
        }
        return substitutionList;
    }

    public String normalizeMeasurement(String measurement, String quantity) {
        if (measurement.equals("Whole")) {
            return measurement;
        }
        if (measurement.equals("Fluid Ounces") && quantity.equals("1")){
            return "Fluid Ounce";
        }
        if (quantity.equals("1")) {
            if (measurement.endsWith("es")) {
                return measurement.substring(0, measurement.length() - 2);
            } else if (measurement.endsWith("s")) {
                return measurement.substring(0, measurement.length() - 1);
            }
        }
        return measurement;
    }
}
