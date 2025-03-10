package com.thesis.recipease.web;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.user.User;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.util.mail.MailService;
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

    @RequestMapping(value = "/admin/account/delete", method = RequestMethod.POST)
    public String processDeleteAccountForm(Model model, @RequestParam("id") int id, RedirectAttributes redirectAttributes){
        Account acct = appService.getAccountById(id);
        if (appService.deleteAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting account. Try again.");
            return "redirect:/profile/view?id=" + id;
        }
        mailService.sendAccountDeletedByModeratorEmail(acct.getEmail());
        return "account/accountDeleted";
    }

    @RequestMapping(value = "/admin/account/activate", method = RequestMethod.POST)
    public String processActivateAccountForm(Model model, @RequestParam("id") int id, RedirectAttributes redirectAttributes){
        Account acct = appService.getAccountById(id);
        if (appService.activateAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error activating account. Try again.");
            return "redirect:/profile/view?id=" + id;
        }
        mailService.sendAccountActivatedByModeratorEmail(acct.getEmail());
        redirectAttributes.addFlashAttribute("message", "This account has been activated.");
        return "redirect:/profile/view?id=" + id;
    }

    @RequestMapping(value = "/admin/account/deactivate", method = RequestMethod.POST)
    public String processDeactivateAccountForm(Model model, @RequestParam("id") int id, RedirectAttributes redirectAttributes){
        Account acct = appService.getAccountById(id);
        if (appService.deactivateAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deactivating account. Try again.");
            return "redirect:/profile/view?id=" + id;
        }
        mailService.sendAccountDeactivatedByModeratorEmail(acct.getEmail());
        redirectAttributes.addFlashAttribute("message", "This account has been deactivated.");
        return "redirect:/profile/view?id=" + id;
    }

    @RequestMapping(value = "/admin/account/change/email", method = RequestMethod.GET)
    public String displayChangeEmailForm(Model model, @RequestParam("id") int id){
        WebAccount webAccount = new WebAccount();
        Account acct = appService.getAccountById(id);
        webAccount.setEmail(acct.getEmail());
        model.addAttribute("webAccount",webAccount);
        model.addAttribute("fromDashboard",false);
        model.addAttribute("id",id);
        return "moderation/changeUserEmail";
    }

    @RequestMapping(value = "/admin/account/change/email", method = RequestMethod.POST)
    public String processChangeEmailForm(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, WebAccount webAccount, @RequestParam("id") int id){
        Account oldAccount = appService.getAccountById(id);
        if(!accountValidator.isEmailValid(webAccount.getEmail())){
            redirectAttributes.addFlashAttribute("error", "Invalid email address.");
            return "redirect:/admin/account/change/email?id=" + id;
        }
        Account newAccount = appService.updateEmailById(id, webAccount.getEmail());
        if(newAccount == null) {
            redirectAttributes.addFlashAttribute("error", "Error changing email. Please try again.");
            return "redirect:/admin/account/change/email?id=" + id;
        }
        redirectAttributes.addFlashAttribute("message", "This user's email has been changed.");
        mailService.sendEmailChangedByModeratorEmail(oldAccount.getEmail(), newAccount.getEmail());
        return "redirect:/profile/view?id=" + id;
    }

    @RequestMapping(value="/userDashboard", method = RequestMethod.GET)
    public String displayUserDashboard(Model model){
        List<User> users = appService.getUsers();
        model.addAttribute("users", users);
        int numActive = 0;
        for(User user : users){
            if(user.isActive()) {
                numActive++;
            }
        }
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("totalActive", numActive);
        return "moderation/userDashboard";
    }


    @RequestMapping(value="/userDashboard/change", method = RequestMethod.GET)
    public String displayChangeEmailUserDashboard(Model model, @RequestParam("id") int id){
        WebAccount webAccount = new WebAccount();
        Account acct = appService.getAccountById(id);
        webAccount.setEmail(acct.getEmail());
        model.addAttribute("webAccount",webAccount);
        model.addAttribute("fromDashboard",true);
        model.addAttribute("id",id);
        return "moderation/changeUserEmail";
    }

    @RequestMapping(value="/userDashboard/change", method = RequestMethod.POST)
    public String processChangeEmailUserDashboard(Model model, RedirectAttributes redirectAttributes, WebAccount webAccount, @RequestParam("id") int id){
        Account oldAccount = appService.getAccountById(id);
        if(!accountValidator.isEmailValid(webAccount.getEmail())){
            redirectAttributes.addFlashAttribute("error", "Invalid email address.");
            return "redirect:/userDashboard/change?id=" + id;
        }
        Account newAccount = appService.updateEmailById(id, webAccount.getEmail());
        if(newAccount == null){
            redirectAttributes.addFlashAttribute("error", "Error changing email. Please try again.");
            return "redirect:/userDashboard/change?id=" + id;
        }
        redirectAttributes.addFlashAttribute("message", "Email has been changed.");
        mailService.sendEmailChangedByModeratorEmail(oldAccount.getEmail(), newAccount.getEmail());
        return "redirect:/userDashboard";
    }

    @RequestMapping(value="/userDashboard/delete", method = RequestMethod.POST)
    public String processDeleteUserDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam("email") String email){
        if (appService.deleteAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deleting account. Try again.");
            return "redirect:/userDashboard";
        }
        mailService.sendAccountDeletedByModeratorEmail(email);
        redirectAttributes.addFlashAttribute("message", "Account has been deleted.");
        return "redirect:/userDashboard";
    }

    @RequestMapping(value="/userDashboard/activate", method = RequestMethod.POST)
    public String processActivateUserDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam("email") String email){
        if (appService.activateAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error activating account. Try again.");
            return "redirect:/userDashboard";
        }
        mailService.sendAccountActivatedByModeratorEmail(email);
        redirectAttributes.addFlashAttribute("message", "Account has been activated.");
        return "redirect:/userDashboard";
    }

    @RequestMapping(value="/userDashboard/deactivate", method = RequestMethod.POST)
    public String processDeactivateUserDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam("email") String email){
        if (appService.deactivateAccountById(id) == -1) {
            redirectAttributes.addFlashAttribute("error", "Error deactivating account. Try again.");
            return "redirect:/userDashboard";
        }
        mailService.sendAccountDeactivatedByModeratorEmail(email);
        redirectAttributes.addFlashAttribute("message", "Account has been deactivated.");
        return "redirect:/userDashboard";
    }
}
