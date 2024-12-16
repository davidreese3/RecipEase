package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.RegistrationForm;
import com.thesis.recipease.model.WebAccount;
import com.thesis.recipease.model.WebProfile;
import com.thesis.recipease.util.mail.service.MailService;
import com.thesis.recipease.util.validator.AccountValidator;
import com.thesis.recipease.util.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private AccountValidator accountValidator;
    @Autowired
    private ProfileValidator profileValidator;
    @Autowired
    private AppService appService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // =======================
    // Register Account
    // =======================
    @RequestMapping(value = "/account/register", method = RequestMethod.GET)
    public String displayRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "account/registration";
    }

    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    public String processRegistrationForm(Model model, RegistrationForm registrationForm) {
        WebAccount webAccount = registrationForm.getWebAccount();
        webAccount.setEmail(webAccount.getEmail().toLowerCase());
        WebProfile webProfile = registrationForm.getWebProfile();
        webProfile.setEmail(webAccount.getEmail());
        if (!accountValidator.isAccountValid(model, webAccount) || !profileValidator.isProfileValid(model, webProfile)) {
            model.addAttribute(new RegistrationForm());
            return "account/registration";
        }
        List<String> roles = List.of("ROLE_USER");
        webAccount.setPassword(passwordEncoder.encode(webAccount.getPassword()));
        Account account = appService.addAccount(webAccount, roles, webProfile);
        String activationLink = "http://localhost:8080/account/activate?email=" + account.getEmail();
        mailService.sendActivationEmail(webAccount.getEmail(), activationLink, account.getActivationCode());
        model.addAttribute("email", webAccount.getEmail());
        return "account/registrationConfirmation";
    }

    // =======================
    // Activate Account
    // =======================
    @RequestMapping(value = "/account/activate", method = RequestMethod.GET)
    public String displayActivationForm(Model model, @RequestParam("email") String email) {
        model.addAttribute("email", email);
        return "account/activation";
    }

    @RequestMapping(value = "/account/activate", method = RequestMethod.POST)
    public String processActivationForm(Model model, @RequestParam("email") String email, @RequestParam("code") int code) {
        if (!appService.verifyActivationCodeAndActivate(email,code)){
            model.addAttribute("email",email);
            model.addAttribute("error", "Invalid Activation Code");
            return "account/activation";
        }
        return "account/activationConfirmation";
    }
}