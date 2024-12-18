package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.RegistrationForm;
import com.thesis.recipease.model.WebAccount;
import com.thesis.recipease.model.WebProfile;
import com.thesis.recipease.util.mail.service.MailService;
import com.thesis.recipease.util.validator.AccountValidator;
import com.thesis.recipease.util.validator.ProfileValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
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
        int activationCode = appService.getActivationCodeByEmail(email);
        if (!appService.verifyActivationCodeAndActivate(email,code,activationCode)){
            model.addAttribute("email",email);
            model.addAttribute("error", "Invalid Activation Code");
            return "account/activation";
        }
        return "account/activationConfirmation";
    }

    @RequestMapping(value = "account/edit/email", method = RequestMethod.GET)
    public String displayEditAccountForm(Model model, Principal principal){
        WebAccount webAccount = new WebAccount();
        webAccount.setEmail(principal.getName());
        model.addAttribute("webAccount",webAccount);
        return "account/editEmail";
    }

    @RequestMapping(value = "/account/edit/email", method = RequestMethod.POST)
    public String processEditAccountEmailForm(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response, WebAccount webAccount){
        if(!accountValidator.isEmailValid(webAccount.getEmail())){
            model.addAttribute("error", "Invalid email address.");
            return "account/editEmail";
        }
        else if(principal.getName().equals(webAccount.getEmail())){
            model.addAttribute("message","The email you entered is the same as your current one. No changes have been made.");
            return "account/editEmail";
        }
        appService.updateEmailByEmail(principal.getName(), webAccount.getEmail());
        model.addAttribute("success", "Your email has been updated. Please log in again.");
        invalidateSession(request, response);
        return "login";
    }

    @RequestMapping(value = "account/edit/password", method = RequestMethod.GET)
    public String displayEditAccountPasswordForm(Model model){
        WebAccount webAccount = new WebAccount();
        model.addAttribute("webAccount",webAccount);
        return "account/editPassword";
    }

    @RequestMapping(value = "account/edit/password", method = RequestMethod.POST)
    public String displayEditAccountPasswordForm(Model model, Principal principal, WebAccount webAccount){
        if(!accountValidator.isPasswordValid(webAccount.getPassword())){
            model.addAttribute("error", "Password is not strong enough. It must include:");
            List<String> passwordCriteria = Arrays.asList(
                    "At least one lowercase letter",
                    "At least one uppercase letter",
                    "At least one special character",
                    "A minimum length of 8 characters",
                    "No spaces at all."
            );
            model.addAttribute("passwordCriteria",passwordCriteria);
            return "account/editPassword";
        }
        if(!accountValidator.arePasswordsMatching(webAccount.getPassword(), webAccount.getConfirmPassword())){
            model.addAttribute("error", "Passwords do not match.");
            return "account/editPassword";
        }
        if(passwordEncoder.matches(webAccount.getPassword(),appService.getPasswordByEmail(principal.getName()))){
            model.addAttribute("message","The password you entered is the same as your current one. No changes have been made.");
            return "account/editPassword";
        }
        webAccount.setPassword(passwordEncoder.encode(webAccount.getPassword()));
        appService.updatePasswordByEmail(principal.getName(), webAccount.getPassword());
        mailService.sendPasswordResetEmail(principal.getName());
        model.addAttribute("success", "Your password has been updated.");
        return "account/editPassword";
    }

    @RequestMapping(value = "/account/delete", method = RequestMethod.GET)
    public String displayDeleteForm(Model model){
        WebAccount webAccount = new WebAccount();
        model.addAttribute("webAccount",webAccount);
        return "account/deleteAccount";
    }

    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    public String ProcessDeleteForm(Model model, HttpServletRequest request, Principal principal, HttpServletResponse response, WebAccount webAccount){
        if(!passwordEncoder.matches(webAccount.getPassword(),appService.getPasswordByEmail(principal.getName()))){
            model.addAttribute("error","The password you entered is incorrect.");
            return "account/deleteAccount";
        }
        appService.deleteAccountByEmail(principal.getName());
        mailService.sendAccountDeletionEmail(principal.getName());
        invalidateSession(request, response);
        model.addAttribute("success","Your account has been deleted.");
        return "login";
    }
    // ------------------------------------------------
    // HELPER METHODS
    // ------------------------------------------------
    public static void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
    }
}