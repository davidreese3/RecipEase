package com.thesis.recipease.util.validator.recipe.util;

import com.thesis.recipease.model.web.recipe.util.WebComment;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CommentValidator implements Validator<WebComment, ArrayList<String>> {
    public ArrayList<String> validate(WebComment webComment){
        ArrayList<String> errors = new ArrayList<String>();
        if(webComment.getCommentText() == null){
            errors.add("You cannot post a blank comment.");
        }
        return errors;
    }
}
