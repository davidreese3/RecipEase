package com.thesis.recipease.util.validator.recipe.util;

import com.thesis.recipease.model.web.recipe.util.WebComment;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CommentValidator implements Validator<WebComment, ArrayList<String>> {
    ArrayList<String> errors;
    public ArrayList<String> validate(WebComment webComment){
        if(webComment.getCommentText() == null){
            errors.addAll(Arrays.asList("You cannot post a blank comment."));
            return errors;
        }
        return null;
    }
}
