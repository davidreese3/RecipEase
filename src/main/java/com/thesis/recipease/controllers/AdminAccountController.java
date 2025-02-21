package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.user.User;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.util.mail.service.MailService;
import com.thesis.recipease.util.validator.account.AccountValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminAccountController {
    @Autowired
    private AccountValidator accountValidator;
    @Autowired
    private AppService appService;
    @Autowired
    private MailService mailService;

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
            redirectAttributes.addFlashAttribute("error", "Invalid email address.");
            return "redirect:/account/change/email?id=" + id;
        }
        Account newAccount = appService.updateEmailById(id, webAccount.getEmail());
        redirectAttributes.addFlashAttribute("message", "This user's email has been changed.");
        mailService.sendEmailChangedByModeratorEmail(oldAccount.getEmail(), newAccount.getEmail());
        return "redirect:/profile/view?id=" + id;
    }

    @RequestMapping(value="/adminDashboard", method = RequestMethod.GET)
    public String displayAdminDashboard(Model model){
        List<User> users = appService.getUsers();
        model.addAttribute("users", users);
        return "moderation/adminDashboard";
    }


    @RequestMapping(value="/adminDashboard/change", method = RequestMethod.GET)
    public String displayChangeEmailAdminDashboard(Model model, @RequestParam("id") int id){
        WebAccount webAccount = new WebAccount();
        Account acct = appService.getAccountById(id);
        webAccount.setEmail(acct.getEmail());
        model.addAttribute("webAccount",webAccount);
        model.addAttribute("fromDashboard",true);
        model.addAttribute("id",id);
        return "moderation/changeUserEmail";
    }

    @RequestMapping(value="/adminDashboard/change", method = RequestMethod.POST)
    public String processChangeEmailAdminDashboard(Model model, RedirectAttributes redirectAttributes, WebAccount webAccount, @RequestParam("id") int id){
        Account oldAccount = appService.getAccountById(id);
        if(!accountValidator.isEmailValid(webAccount.getEmail())){
            redirectAttributes.addFlashAttribute("error", "Invalid email address.");
            return "redirect:/adminDashboard/change?id=" + id;
        }
        Account newAccount = appService.updateEmailById(id, webAccount.getEmail());
        redirectAttributes.addFlashAttribute("message", "This user's email has been changed.");
        mailService.sendEmailChangedByModeratorEmail(oldAccount.getEmail(), newAccount.getEmail());
        return "redirect:/adminDashboard";
    }

    @RequestMapping(value="/adminDashboard/delete", method = RequestMethod.POST)
    public String processDeleteAdminDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam("email") String email){
        if (appService.deleteAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting account. Try again.");
            return "redirect:/adminDashboard";
        }
        mailService.sendAccountDeletionEmail(email);
        redirectAttributes.addFlashAttribute("message", "This account has been deleted.");
        return "redirect:/adminDashboard";
    }

    @RequestMapping(value="/adminDashboard/activate", method = RequestMethod.POST)
    public String processActivateAdminDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam("email") String email){
        if (appService.reactivateAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error activating account. Try again.");
            return "redirect:/adminDashboard";
        }
        mailService.sendAccountReactivatedByModeratorEmail(email);
        redirectAttributes.addFlashAttribute("message", "This account has been reactivated.");
        return "redirect:/adminDashboard";
    }
}
