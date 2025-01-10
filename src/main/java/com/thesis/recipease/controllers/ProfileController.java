package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.Profile;
import com.thesis.recipease.model.recipe.RecipeInfo;
import com.thesis.recipease.model.web.WebProfile;
import com.thesis.recipease.util.normalizer.RecipeNormalizer;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private ProfileValidator profileValidator;
    @Autowired
    private AppService appService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private RecipeNormalizer recipeNormalizer;

    @RequestMapping(value = "/profile/view/personal", method = RequestMethod.GET)
    public String displayPersonalProfile(Model model){
        int userId = securityService.getLoggedInUserId();
        Profile profile = appService.getProfileById(userId);
        if(profile == null){
            model.addAttribute("error","You do not have a profile created.");
        }
        else{
            model.addAttribute("profile", profile);
            List<RecipeInfo> recipes = appService.getRecipeByUserId(userId);
            model.addAttribute("recipes", recipes);
            for(RecipeInfo recipeInfo : recipes) {
                recipeInfo = recipeNormalizer.normalizeRecipeInfo(recipeInfo);
            }
        }
        model.addAttribute("isPersonal",true);
        return "profile/viewProfile";
    }

    @RequestMapping(value = "/profile/view", method = RequestMethod.GET)
    public String displayOtherProfile(Model model, @RequestParam("id") int id){
        if(id == securityService.getLoggedInUserId()){
            displayPersonalProfile(model);
        }
        else {
            Profile profile = appService.getProfileById(id);
            if (profile == null) {
                model.addAttribute("error", "There is no profile associated with this user");
            } else {
                model.addAttribute("profile", profile);
                List<RecipeInfo> recipes = appService.getRecipeByUserId(id);
                for (RecipeInfo recipeInfo : recipes) {
                    recipeInfo = recipeNormalizer.normalizeRecipeInfo(recipeInfo);
                }
                model.addAttribute("recipes", recipes);
            }
            model.addAttribute("isPersonal", false);
        }
        return "profile/viewProfile";
    }

    @RequestMapping(value = "profile/edit", method = RequestMethod.GET)
    public String displayEditProfileForm(Model model, Principal principal){
        Profile profile = appService.getProfileById(securityService.getLoggedInUserId());
        model.addAttribute("webProfile", profile);
        return "profile/editProfile";
    }
    @RequestMapping(value = "profile/edit", method = RequestMethod.POST)
    public String processEditProfileForm(Model model, Principal principal,RedirectAttributes redirectAttributes, WebProfile webProfile){
        webProfile.setId(securityService.getLoggedInUserId());
        if (!profileValidator.isProfileValid(model, webProfile)){
            return "profile/editProfile";
        }
        appService.updateProfile(webProfile);
        //needed bc redirect creates new HTTP request
        redirectAttributes.addFlashAttribute("message", "Your profile has been updated!");
        return "redirect:/profile/view/personal";
    }

}
