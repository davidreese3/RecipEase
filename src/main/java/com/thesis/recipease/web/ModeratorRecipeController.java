package com.thesis.recipease.web;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ModeratorRecipeController {
    @Autowired
    AppService appService;

    @RequestMapping(value = "/mod/recipe/delete", method = RequestMethod.POST)
    public String deleteRecipe(Model model, @RequestParam("recipeId") int recipeId, RedirectAttributes redirectAttributes) {
        if (appService.deleteRecipeByRecipeId(recipeId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting recipe. Try again.");
            return "redirect:/recipe/view?recipeId=" + recipeId;
        }
        return "recipe/recipeDeleted";
    }

    @RequestMapping(value = "/mod/recipe/comment/delete", method = RequestMethod.POST)
    public String deleteComment(Model model, @RequestParam("commentId") int commentId, @RequestParam("recipeId") int recipeId, @RequestParam("comment") String comment, RedirectAttributes redirectAttributes) {
        if (appService.deleteCommentByCommentId(commentId) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting recipe. Try again.");
            return "redirect:/recipe/view?recipeId=" + recipeId;
        }
        redirectAttributes.addFlashAttribute("message", "The comment (\'"+comment+"\') has be deleted. ");
        return "redirect:/recipe/view?recipeId=" + recipeId;
    }

    @RequestMapping(value = "/recipeDashboard", method = RequestMethod.GET)
    public String displayRecipeDashboard(Model model){
        List<RecipeInfo> recipes = appService.getRecipes();
        model.addAttribute("recipes", recipes);
        int numTrending = 0;
        for(RecipeInfo recipe : recipes){
            if(recipe.isStaffTrending()) {
                numTrending++;
            }
        }
        model.addAttribute("totalRecipes", recipes.size());
        model.addAttribute("totalTrending", numTrending);
        return "moderation/recipeDashboard";
    }

    @RequestMapping(value = "/recipeDashboard/delete", method = RequestMethod.POST)
    public String processDeleteRecipeDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id){
        if (appService.deleteRecipeByRecipeId(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting recipe. Try again.");
            return "redirect:/recipeDashboard";
        }
        redirectAttributes.addFlashAttribute("message","Recipe has been deleted. Thank you for cleaning up RecipEase!");
        return "redirect:/recipeDashboard";
    }

    @RequestMapping(value = "/recipeDashboard/trending/add", method = RequestMethod.POST)
    public String processTrendingAddRecipeDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id){
        if (appService.addRecipeToTrendingById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error adding recipe. Try again.");
            return "redirect:/recipeDashboard";
        }
        redirectAttributes.addFlashAttribute("message","Recipe has been added to trending.");
        return "redirect:/recipeDashboard";
    }

    @RequestMapping(value = "/recipeDashboard/trending/remove", method = RequestMethod.POST)
    public String processTrendingRemoveRecipeDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id){
        if (appService.removeRecipeFromTrendingById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error removing recipe. Try again.");
            return "redirect:/recipeDashboard";
        }
        redirectAttributes.addFlashAttribute("message","Recipe has been removed from trending.");
        return "redirect:/recipeDashboard";
    }

}
