package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.profile.Profile;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import com.thesis.recipease.model.web.profile.WebProfile;
import com.thesis.recipease.util.generator.ProfileErrorMessageGenerator;
import com.thesis.recipease.util.normalizer.recipe.RecipeNormalizer;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.validator.profile.ProfileValidator;
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
            model.addAttribute("message",ProfileErrorMessageGenerator.getPersonalProfileError());
            return "profile/viewProfileDNE";
        }
        else{
            model.addAttribute("profile", profile);
            List<RecipeInfo> recipes = appService.getRecipesByUserId(userId);
            for(RecipeInfo recipeInfo : recipes) {
                recipeInfo = recipeNormalizer.normalizeRecipeInfo(recipeInfo);
            }
            model.addAttribute("recipes", recipes);
        }
        model.addAttribute("isPersonal",true);
        return "profile/viewProfile";
    }

    @RequestMapping(value = "/profile/view", method = RequestMethod.GET)
    public String displayOtherProfile(Model model, @RequestParam("id") int id){
        if(id == securityService.getLoggedInUserId()){
            return "redirect:/profile/view/personal";
        }
        else {
            Profile profile = appService.getProfileById(id);
            if(profile == null){
                model.addAttribute("message",ProfileErrorMessageGenerator.getProfileError());
                return "profile/viewProfileDNE";
            }
            else {
                model.addAttribute("profile", profile);
                List<RecipeInfo> recipes = appService.getRecipesByUserId(id);
                for (RecipeInfo recipeInfo : recipes) {
                    recipeInfo = recipeNormalizer.normalizeRecipeInfo(recipeInfo);
                }
                model.addAttribute("recipes", recipes);

                model.addAttribute("isPersonal", false);
                if (!profile.isActive()){
                    model.addAttribute("error", ProfileErrorMessageGenerator.getDeactivatedProfileError());
                }
            }
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
