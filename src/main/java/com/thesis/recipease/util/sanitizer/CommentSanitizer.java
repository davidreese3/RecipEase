package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebComment;
import org.springframework.stereotype.Service;

@Service
public class CommentSanitizer{
    public WebComment sanitize(WebComment webComment){
        webComment.setCommentText(webComment.getCommentText().trim());
        if(webComment.getCommentText().isEmpty()){
            webComment.setCommentText(null);
        }
        return webComment;
    }
}
