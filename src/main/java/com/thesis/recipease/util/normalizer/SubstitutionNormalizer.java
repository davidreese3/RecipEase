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

    public static String fixMeasurement(String measurement, Integer wholeNumberQuantity, String fractionQuantity) {
        if (measurement.equals("Whole")) {
            return measurement;
        }
        if (measurement != null && wholeNumberQuantity == 1 && fractionQuantity.equals("0")) {
            if (measurement.endsWith("es")) {
                return measurement.substring(0, measurement.length() - 2);
            } else if (measurement.endsWith("s")) {
                return measurement.substring(0, measurement.length() - 1);
            }
        }
        return measurement;
    }
}
