package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ModeratorRecipeController {
    @Autowired
    AppService appService;

    @RequestMapping(value = "/recipe/delete", method = RequestMethod.POST)
    public String deleteRecipe(Model model, @RequestParam("recipeId") int recipeId, RedirectAttributes redirectAttributes) {
        if (appService.deleteRecipeByRecipeId(recipeId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting recipe. Try again.");
            return "redirect:/recipe/view?recipeId=" + recipeId;
        }
        return "recipe/recipeDeleted";
    }

    @RequestMapping(value = "/recipe/comment/delete", method = RequestMethod.POST)
    public String deleteComment(Model model, @RequestParam("commentId") int commentId, @RequestParam("recipeId") int recipeId, @RequestParam("comment") String comment, RedirectAttributes redirectAttributes) {
        if (appService.deleteCommentByCommentId(commentId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting recipe. Try again.");
            return "redirect:/recipe/view?recipeId=" + recipeId;
        }
        redirectAttributes.addFlashAttribute("message", "The comment (\'"+comment+"\') has be deleted. ");
        return "redirect:/recipe/view?recipeId=" + recipeId;
    }
}
