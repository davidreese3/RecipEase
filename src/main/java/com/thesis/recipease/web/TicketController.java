package com.thesis.recipease.web;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import com.thesis.recipease.model.domain.ticket.Ticket;
import com.thesis.recipease.model.web.ticket.TicketCriteria;
import com.thesis.recipease.model.web.ticket.WebTicket;
import com.thesis.recipease.util.sanitizer.ticket.TicketSanitizer;
import com.thesis.recipease.util.validator.ticket.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    private AppService appService;
    @Autowired
    private TicketValidator ticketValidator;
    @Autowired
    private TicketSanitizer ticketSanitizer;


    @RequestMapping(value = "/ticket/create", method = RequestMethod.GET)
    public String displayTicketCreateForm(Model model){
        model.addAttribute(new WebTicket());
        return "ticket/createTicket";
    }

    @RequestMapping(value = "/ticket/create", method = RequestMethod.POST)
    public String processTicketCreateForm(Model model, WebTicket webTicket){
        webTicket = ticketSanitizer.sanitize(webTicket);
        ArrayList<String> errors = ticketValidator.validate(webTicket);
        if (errors.size() != 0){
            model.addAttribute("errors", errors);
        }
        else{
            model.addAttribute("message","Your ticket has been successfully submitted.");
            appService.addTicket(webTicket);
            webTicket = new WebTicket();
        }
        model.addAttribute(webTicket);
        return "ticket/createTicket";
    }

    @RequestMapping(value = "/ticketDashboard", method = RequestMethod.GET)
    public String displayTicketDashboard(Model model){
        List<Ticket> tickets = appService.getAllTickets();
        model.addAttribute("tickets", tickets);
        int totalOpen = 0;
        int totalIssues = 0;
        int totalSuggestions = 0;

        for (Ticket ticket : tickets) {
            if (ticket.isOpen()) {
                totalOpen++;
            }
            if (ticket.getClassifier().equals("Issue")) {
                totalIssues++;
            } else {
                totalSuggestions++;
            }
        }

        model.addAttribute("totalTickets", tickets.size());
        model.addAttribute("totalOpen", totalOpen);
        model.addAttribute("totalIssues", totalIssues);
        model.addAttribute("totalSuggestions", totalSuggestions);
        return "moderation/ticketDashboard";
    }

    /* Use later for search
    @RequestMapping(value = "/ticketDashboard", method = RequestMethod.GET)
    public String displayTicketDashboard(Model model,
                                @RequestParam(value = "classifier", required = false) String classifier,
                                @RequestParam(value = "solved", required = false) String solved) {
        TicketCriteria ticketCriteria = new TicketCriteria(classifier, solved);
        List<Ticket> tickets = appService.getTicketsByCriteria(ticketCriteria);
        model.addAttribute("tickets", tickets);
        //something about text of what is displayed
        String display = "You are viewing ";
        if(solved != null){ display += solved + " "; }
        if(classifier != null){ display +=  classifier + "s."; }
        model.addAttribute("display",display);
        return "moderation/ticketDashboard";
    }
    */
    @RequestMapping(value="/ticketDashboard/statusChange", method = RequestMethod.POST)
    public String processTicketStatusChangeFromDashboard(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam(value = "open") boolean open){
        int changed = appService.updateTicketOpenStatus(id,open);
        if(changed == -1){redirectAttributes.addFlashAttribute("error", "Error changing status. Please try again.");}
        else {redirectAttributes.addFlashAttribute("message","Status has been changed.");}
        return "redirect:/ticketDashboard";
    }

    @RequestMapping(value="/ticket/statusChange", method = RequestMethod.POST)
    public String processTicketStatusChangeFromTicket(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam(value = "open") boolean open){
        int changed = appService.updateTicketOpenStatus(id,open);
        if(changed == -1){redirectAttributes.addFlashAttribute("error", "Error changing status. Please try again.");}
        else {redirectAttributes.addFlashAttribute("message","Status has been changed.");}
        return "redirect:/ticket/view?id=" + id;
    }

    @RequestMapping(value="/ticket/view", method = RequestMethod.GET)
    public String viewTicket(Model model, @RequestParam("id") int id){
        Ticket ticket = appService.getTicketById(id);
        if(ticket == null){
          model.addAttribute("error","Error loading ticket, or ticket does not exist.");
          return "moderation/viewTicketDNE";
        }
        else {
        model.addAttribute("ticket", ticket);
        return "moderation/viewTicket";
        }
    }

}
