package com.thesis.recipease.util.sanitizer.recipe.util;

import com.thesis.recipease.model.web.recipe.util.WebComment;
import com.thesis.recipease.util.sanitizer.Sanitizer;
import org.springframework.stereotype.Service;

@Service
public class CommentSanitizer implements Sanitizer<WebComment> {
    public WebComment sanitize(WebComment webComment){
        webComment.setCommentText(webComment.getCommentText().trim());
        if(webComment.getCommentText().isEmpty()){
            webComment.setCommentText(null);
        }
        return webComment;
    }
}
