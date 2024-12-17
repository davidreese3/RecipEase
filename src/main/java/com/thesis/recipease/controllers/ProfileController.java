package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.Profile;
import com.thesis.recipease.model.WebProfile;
import com.thesis.recipease.util.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    private ProfileValidator profileValidator;
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/profile/view/personal", method = RequestMethod.GET)
    public String displayPersonalProfile(Model model, Principal principal){
        Profile profile = appService.getProfileByEmail(principal.getName());
        if(profile == null){
            model.addAttribute("error","You do not have a profile created.");
        }
        else{
            model.addAttribute("profile", profile);
        }
        model.addAttribute("isPersonal",true);
        return "profile/viewProfile";
    }

    @RequestMapping(value = "/profile/view", method = RequestMethod.GET)
    public String displayOtherProfile(Model model, @RequestParam("email") String email){
        Profile profile = appService.getProfileByEmail(email);
        if(profile == null){
            model.addAttribute("error","There is no profile associated with this user");
        }
        else{
            model.addAttribute("profile", profile);
        }
        model.addAttribute("isPersonal",false);
        return "profile/viewProfile";
    }

    @RequestMapping(value = "profile/edit", method = RequestMethod.GET)
    public String displayEditProfileForm(Model model, Principal principal){
        Profile profile = appService.getProfileByEmail(principal.getName());
        model.addAttribute("webProfile", profile);
        return "profile/editProfile";
    }
    @RequestMapping(value = "profile/edit", method = RequestMethod.POST)
    public String processEditProfileForm(Model model, Principal principal, WebProfile webProfile, RedirectAttributes redirectAttributes){
        webProfile.setEmail(principal.getName());
        if (!profileValidator.isProfileValid(model,webProfile)){
            return "profile/editProfile";
        }
        appService.updateProfile(webProfile);
        //needed bc redirect creates new HTTP request
        redirectAttributes.addFlashAttribute("message", "Your profile has been updated!");
        return "redirect:/profile/view/personal";
    }

}
