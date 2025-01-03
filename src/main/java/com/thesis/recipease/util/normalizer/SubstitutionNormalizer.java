package com.thesis.recipease.util.normalizer;

import com.thesis.recipease.model.SubstitutionEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubstitutionNormalizer {
    public List<SubstitutionEntry> normalize(List<SubstitutionEntry> substitutionList){
        for (SubstitutionEntry substitution : substitutionList) {
            String fixedOriginal = fixMeasurement(
                    substitution.getOriginalMeasurement(),
                    substitution.getOriginalWholeNumberQuantity(),
                    substitution.getOriginalFractionQuantity()
            );
            String fixedSubstituted = fixMeasurement(
                    substitution.getSubstitutedMeasurement(),
                    substitution.getSubstitutedWholeNumberQuantity(),
                    substitution.getSubstitutedFractionQuantity()
            );
            substitution.setOriginalMeasurement(fixedOriginal);
            substitution.setSubstitutedMeasurement(fixedSubstituted);
        }
        return substitutionList;
    }

    public static String fixMeasurement(String substitutedMeasurement, Integer substitutedWholeNumberQuantity, String substitutedFractionQuantity) {
        if (substitutedMeasurement.equals("Whole")) {
            return substitutedMeasurement;
        }
        if (substitutedMeasurement != null && substitutedWholeNumberQuantity == 1 && substitutedFractionQuantity.equals("0")) {
            if (substitutedMeasurement.endsWith("es")) {
                return substitutedMeasurement.substring(0, substitutedMeasurement.length() - 2);
            } else if (substitutedMeasurement.endsWith("s")) {
                return substitutedMeasurement.substring(0, substitutedMeasurement.length() - 1);
            }
        }
        return substitutedMeasurement;
    }
}
