package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebPhoto;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;

import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoValidator implements Validator<WebRecipe, ArrayList<String>> {
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe) {
        errors = new ArrayList<String>();
        WebPhoto webPhoto = webRecipe.getPhoto();
        if (webPhoto != null) {
            validateFileType(webPhoto);
            validateFileSize(webPhoto);
        }
        return errors;
    }

    private void validateFileType(WebPhoto webPhoto) {
        Tika tika = new Tika();
        MultipartFile file = webPhoto.getFile();

        try (InputStream inputStream = file.getInputStream()) {
            String mimeType = tika.detect(inputStream);

            if (!(mimeType.equals("image/jpeg") ||
                    mimeType.equals("image/pjpeg") ||
                    mimeType.equals("image/png") ||
                    mimeType.equals("image/gif"))) {
                errors.add("Uploaded file type is not supported for photos. Only jpeg, png, and gif are allowed.");
            }
        } catch (IOException e) {
            errors.add("Error reading uploaded file. Please try again.");
        }
    }

    private void validateFileSize(WebPhoto webPhoto) {
        if(webPhoto.getFile().getSize() >= 5242880){
            errors.add("File size is took big. Limit is 5MB.");
        }
    }
}