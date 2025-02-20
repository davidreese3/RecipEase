package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebComment;
import com.thesis.recipease.model.web.recipe.WebNote;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentValidator{
    public String validate(WebComment webComment){
        if(webComment.getCommentText() == null){
            return "You cannot post a blank comment.";
        }
        return null;
    }
}
