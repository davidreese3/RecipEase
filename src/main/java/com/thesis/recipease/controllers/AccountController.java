package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.domain.form.RegistrationForm;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.profile.WebProfile;
import com.thesis.recipease.util.mail.service.MailService;
import com.thesis.recipease.util.security.SecurityService;
import com.thesis.recipease.util.validator.account.AccountValidator;
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
        if (!accountValidator.isAccountValid(model, webAccount) || !profileValidator.isProfileValid(model, webProfile)) {
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
        int id = securityService.getLoggedInUserId();
        if(passwordEncoder.matches(webAccount.getPassword(),appService.getPasswordById(id))){
            model.addAttribute("message","The password you entered is the same as your current one. No changes have been made.");
            return "account/editPassword";
        }
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
            model.addAttribute("error","The password you entered is incorrect.");
            return "account/deactivateAccount";
        }
        appService.deactivateAccountById(id);
        mailService.sendAccountDeletionEmail(principal.getName());
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
        if(!appService.verifyVerificationCodeAndActivate(id, code, verificationCode)){
            model.addAttribute("id",id);
            model.addAttribute("error", "Invalid Activation Code");
            return "account/resetPassword";
        }
        if(!accountValidator.isPasswordValid(webAccount.getPassword())){
            model.addAttribute("error", "Password is not strong enough. It must include:");
            List<String> passwordCriteria = Arrays.asList(
                    "At least one lowercase letter",
                    "At least one uppercase letter",
                    "At least one special character",
                    "A minimum length of 8 characters",
                    "No spaces at all."
            );
            model.addAttribute("id",id);
            model.addAttribute("passwordCriteria",passwordCriteria);
            return "account/resetPassword";
        }
        if(!accountValidator.arePasswordsMatching(webAccount.getPassword(), webAccount.getConfirmPassword())){
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("id",id);
            return "account/resetPassword";
        }
        webAccount.setPassword(passwordEncoder.encode(webAccount.getPassword()));
        Account account = appService.updatePasswordById(id, webAccount.getPassword());
        model.addAttribute("success", "Your password has been updated.");
        return "account/resetPassword";
    }

    @RequestMapping(value = "account/delete", method = RequestMethod.POST)
    public String processDeleteAccountForm(Model model, @RequestParam("id") int id, RedirectAttributes redirectAttributes){
        Account acct = appService.getAccountById(id);
        if (appService.deleteAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting account. Try again.");
            return "redirect:/profile/view?id=" + id;
        }
        mailService.sendAccountDeletedByModeratorEmail(acct.getEmail());
        return "account/accountDeleted";
    }

    @RequestMapping(value = "account/reactivate", method = RequestMethod.POST)
    public String processReactivateAccountPasswordForm(Model model, @RequestParam("id") int id, RedirectAttributes redirectAttributes){
        Account acct = appService.getAccountById(id);
        if (appService.reactivateAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error reactivating account. Try again.");
            return "redirect:/profile/view?id=" + id;
        }
        mailService.sendAccountReactivatedByModeratorEmail(acct.getEmail());
        redirectAttributes.addFlashAttribute("message", "This account has been reactivated.");
        return "redirect:/profile/view?id=" + id;
    }

    @RequestMapping(value = "account/change/email", method = RequestMethod.GET)
    public String displayChangeEmailForm(Model model, @RequestParam("id") int id){
        WebAccount webAccount = new WebAccount();
        Account acct = appService.getAccountById(id);
        webAccount.setEmail(acct.getEmail());
        model.addAttribute("webAccount",webAccount);
        model.addAttribute("fromDashboard",false);
        model.addAttribute("id",id);
        return "moderation/changeUserEmail";
    }

    @RequestMapping(value = "/account/change/email", method = RequestMethod.POST)
    public String processChangeEmailForm(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, WebAccount webAccount, @RequestParam("id") int id){
        Account oldAccount = appService.getAccountById(id);
        if(!accountValidator.isEmailValid(webAccount.getEmail())){
            model.addAttribute("error", "Invalid email address.");
            return "moderation/changeUserEmail";
        }
        Account newAccount = appService.updateEmailById(id, webAccount.getEmail());
        redirectAttributes.addFlashAttribute("message", "This user's email has been changed.");
        mailService.sendEmailChangedByModeratorEmail(oldAccount.getEmail(), newAccount.getEmail());
        return "redirect:/profile/view?id=" + id;
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