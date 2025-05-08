package com.thesis.recipease.web;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.web.ticket.WebTicket;
import com.thesis.recipease.util.sanitizer.ticket.TicketSanitizer;
import com.thesis.recipease.util.validator.ticket.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

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
}
