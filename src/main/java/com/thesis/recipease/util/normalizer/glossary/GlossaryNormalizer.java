package com.thesis.recipease.util.normalizer.glossary;

import com.thesis.recipease.util.normalizer.Normalizer;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GlossaryNormalizer implements Normalizer<String> {
    HashMap<String, String> replacements;

    public GlossaryNormalizer(){
        replacements = new HashMap<>();
        replacements.put("al-dente","al dente");
        replacements.put("bain marie","bain-marie");
        replacements.put("flambe","flambé");
        replacements.put("fluid-ounce","fluid ounce");
        replacements.put("mise en place","mise-en-place");
        replacements.put("pan fry","pan-fry");
        replacements.put("saute","sauté");
    }

    public String normalize(String contents) {
        for(HashMap.Entry<String, String> entry : replacements.entrySet()){
            contents = contents.replaceAll(entry.getKey(), entry.getValue());
        }
        return contents;
    }
}
