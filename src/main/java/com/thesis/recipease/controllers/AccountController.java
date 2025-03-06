package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.domain.form.RegistrationForm;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.profile.WebProfile;
import com.thesis.recipease.util.mail.service.MailService;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.validator.account.AccountValidator;
import com.thesis.recipease.util.validator.account.ValidationResults;
import com.thesis.recipease.util.validator.profile.ProfileValidator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thesis.recipease.util.generator.VerificationCodeGenerator;


import java.security.Principal;
import java.util.ArrayList;
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
    @Autowired
    private SecurityService securityService;

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
        webProfile.setId(0);
        ValidationResults validationResults = accountValidator.validate(webAccount);
        ArrayList<String> errors = validationResults.getErrors();
        errors.addAll(profileValidator.validate(webProfile));
        ArrayList<String> passwordCriteria = validationResults.getPasswordCriteria();
        System.out.println(passwordCriteria.toString());
        if(errors.size() > 0){
            model.addAttribute("errors", errors);
        }
        if(passwordCriteria.size() > 0){
            model.addAttribute("passwordCriteria", passwordCriteria);
        }
        if (errors.size() > 0 || passwordCriteria.size() > 0) {
            model.addAttribute(registrationForm);
            return "account/registration";
        }
        List<String> roles = List.of("ROLE_USER");
        webAccount.setPassword(passwordEncoder.encode(webAccount.getPassword()));
        webAccount.setVerificationCode(VerificationCodeGenerator.generateVerificationCode());
        Account account = appService.addAccount(webAccount, roles, webProfile);
        String activationLink = "http://localhost:8080/account/activate?id=" + account.getId();
        mailService.sendActivationEmail(webAccount.getEmail(), activationLink, account.getVerificationCode());
        model.addAttribute("email", webAccount.getEmail());
        return "account/registrationConfirmation";
    }

    // =======================
    // Activate Account
    // =======================
    @RequestMapping(value = "/account/activate", method = RequestMethod.GET)
    public String displayActivationForm(Model model, @RequestParam("id") int id) {
        model.addAttribute("id", id);
        return "account/activation";
    }

    @RequestMapping(value = "/account/activate", method = RequestMethod.POST)
    public String processActivationForm(Model model, @RequestParam("id") int id, @RequestParam("code") int code) {
        int verificationCode = appService.getVerificationCodeById(id);
        if (!appService.verifyVerificationCodeAndActivate(id,code,verificationCode)){
            model.addAttribute("id",id);
            model.addAttribute("error", "Invalid Activation Code");
            return "account/activation";
        }
        return "account/activationConfirmation";
    }

    @RequestMapping(value = "account/edit/email", method = RequestMethod.GET)
    public String displayEditEmailForm(Model model, Principal principal){
        WebAccount webAccount = new WebAccount();
        webAccount.setEmail(principal.getName());
        model.addAttribute("webAccount",webAccount);
        return "account/editEmail";
    }

    @RequestMapping(value = "/account/edit/email", method = RequestMethod.POST)
    public String processEditEmailForm(Model model, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, WebAccount webAccount){
        if(!accountValidator.isEmailValid(webAccount.getEmail())){
            model.addAttribute("error", "Invalid email address.");
            webAccount.setEmail(principal.getName());
            return "account/editEmail";
        }
        else if(principal.getName().equals(webAccount.getEmail())){
            model.addAttribute("message","The email you entered is the same as your current one. No changes have been made.");
            webAccount.setEmail(principal.getName());
            return "account/editEmail";
        }
        int id = securityService.getLoggedInUserId();
        Account account = appService.updateEmailById(id, webAccount.getEmail());
        redirectAttributes.addFlashAttribute("success", "Your email has been updated. Please log in again.");
        invalidateSession(request, response);
        return "redirect:/login";
    }

    @RequestMapping(value = "account/edit/password", method = RequestMethod.GET)
    public String displayEditAccountPasswordForm(Model model){
        WebAccount webAccount = new WebAccount();
        model.addAttribute("webAccount",webAccount);
        return "account/editPassword";
    }

    @RequestMapping(value = "account/edit/password", method = RequestMethod.POST)
    public String processEditAccountPasswordForm(Model model, Principal principal, WebAccount webAccount){
        ValidationResults validationResults = accountValidator.validatePassword(webAccount);
        ArrayList<String> passwordCriteria = validationResults.getPasswordCriteria();
        ArrayList<String> errors = validationResults.getErrors();
        if(passwordCriteria.size() > 0){
            model.addAttribute("passwordCriteria",passwordCriteria);
        }
        if(errors.size() > 0){
            model.addAttribute("errors", errors);
        }
        if(passwordCriteria.size() > 0 || errors.size() > 0) {
            return "account/editPassword";
        }
        int id = securityService.getLoggedInUserId();
        webAccount.setPassword(passwordEncoder.encode(webAccount.getPassword()));
        Account account = appService.updatePasswordById(id, webAccount.getPassword());
        mailService.sendPasswordResetEmail(principal.getName());
        model.addAttribute("success", "Your password has been updated.");
        return "account/editPassword";
    }

    @RequestMapping(value = "/account/deactivate", method = RequestMethod.GET)
    public String displayDeactivateForm(Model model){
        WebAccount webAccount = new WebAccount();
        model.addAttribute("webAccount",webAccount);
        return "account/deactivateAccount";
    }

    @RequestMapping(value = "/account/deactivate", method = RequestMethod.POST)
    public String ProcessDeactivateForm(Model model, HttpServletRequest request, Principal principal, HttpServletResponse response, WebAccount webAccount, RedirectAttributes redirectAttributes){
        int id = securityService.getLoggedInUserId();
        if(!passwordEncoder.matches(webAccount.getPassword(),appService.getPasswordById(id))){
            redirectAttributes.addFlashAttribute("error","The password you entered is incorrect.");
            return "redirect:/account/deactivateAccount";
        }
        if(appService.deactivateAccountById(id) == -1){
            redirectAttributes.addFlashAttribute("error","There was an error deactivating your account. Please try again.");
            return "redirect:/account/deactivateAccount";
        }
        mailService.sendAccountDeactivatedEmail(principal.getName());
        invalidateSession(request, response);
        redirectAttributes.addFlashAttribute("success","Your account has been deactivated.");
        return "redirect:/login";
    }

    @RequestMapping(value = "account/reset/password/request", method = RequestMethod.GET)
    public String displayPasswordResetForm(Model model) {
        return "account/resetPasswordRequest";
    }

    @RequestMapping(value = "account/reset/password/request", method = RequestMethod.POST)
    public String processPasswordResetForm(@RequestParam("email") String email, Model model) {
        System.out.println("Inside processPasswordResetForm");
        Account account = appService.getAccountByEmail(email);

        if (account == null) {
            model.addAttribute("error", "No account found with that email.");
            return "login";
        }

        int verificationCode = appService.generateAndSaveVerificationCode(account.getId());
        String resetLink = "http://localhost:8080/account/reset/password?id=" + account.getId();

        mailService.sendResetPasswordEmail(email, resetLink, verificationCode);

        model.addAttribute("success", "A password reset link has been sent to your email.");
        return "login";
    }


    @RequestMapping(value = "account/reset/password", method = RequestMethod.GET)
    public String displayResetAccountPasswordForm(Model model, @RequestParam("id") int id){
        WebAccount webAccount = new WebAccount();
        model.addAttribute("webAccount",webAccount);
        model.addAttribute("id", id);
        return "account/resetPassword";
    }

    @RequestMapping(value = "account/reset/password", method = RequestMethod.POST)
    public String processResetAccountPasswordForm(Model model, WebAccount webAccount, @RequestParam("id") int id, @RequestParam("code") int code){
        int verificationCode = appService.getVerificationCodeById(id);
        ValidationResults validationResults = accountValidator.validatePassword(webAccount);
        ArrayList<String> passwordCriteria = validationResults.getPasswordCriteria();
        ArrayList<String> errors = validationResults.getErrors();
        if(!appService.verifyVerificationCodeAndActivate(id, code, verificationCode)){
            errors.add("Invalid Activation Code");
        }
        if(passwordCriteria.size() > 0){
            model.addAttribute("passwordCriteria",passwordCriteria);
        }
        if(errors.size() > 0){
            model.addAttribute("errors", errors);
        }
        if(errors.size() > 0 || passwordCriteria.size() > 0){
            model.addAttribute("id",id);
            return "account/resetPassword";
        }
        webAccount.setPassword(passwordEncoder.encode(webAccount.getPassword()));
        Account account = appService.updatePasswordById(id, webAccount.getPassword());
        model.addAttribute("success", "Your password has been updated.");
        return "account/resetPassword";
    }

    @RequestMapping(value = "account/reactivate/request", method = RequestMethod.GET)
    public String displayReactivationRequestForm(Model model) {
        return "account/reactivateAccountRequest";
    }

    @RequestMapping(value = "account/reactivate/request", method = RequestMethod.POST)
    public String processReactivationRequestForm(@RequestParam("email") String email, Model model) {
        System.out.println("INSIDE POST");
        Account account = appService.getAccountByEmail(email);
        if (account == null) {
            model.addAttribute("error", "No account found with that email.");
            return "account/reactivateAccountRequest";
        }
        int verificationCode = appService.generateAndSaveVerificationCode(account.getId());
        String activationLink = "http://localhost:8080/account/reactivate?id=" + account.getId();
        mailService.sendReactivationEmail(account.getEmail(), activationLink, verificationCode);
        model.addAttribute("success", "A reactivation code link has been sent to your email.");
        return "login";
    }

    @RequestMapping(value = "account/reactivate", method = RequestMethod.GET)
    public String displayReactivationForm(Model model, @RequestParam("id") int id){
        WebAccount webAccount = new WebAccount();
        model.addAttribute("webAccount",webAccount);
        model.addAttribute("id", id);
        return "account/reactivateAccount";
    }

    @RequestMapping(value = "account/reactivate", method = RequestMethod.POST)
    public String processReactivationForm(Model model, WebAccount webAccount, @RequestParam("id") int id, @RequestParam("code") int code){
        int verificationCode = appService.getVerificationCodeById(id);
        if(!appService.verifyVerificationCodeAndActivate(id, code, verificationCode)){
            model.addAttribute("id",id);
            model.addAttribute("error", "Invalid Reactivation Code");
            return "account/reactivateAccount";
        }
        model.addAttribute("success", "Your account has been reactivated.");
        return "account/reactivateAccount";
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