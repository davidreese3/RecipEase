package com.thesis.recipease.util.sanitizer.ticket;

import com.thesis.recipease.model.web.ticket.WebTicket;
import com.thesis.recipease.util.sanitizer.Sanitizer;
import org.springframework.stereotype.Service;

@Service
public class TicketSanitizer implements Sanitizer<WebTicket> {
    public WebTicket sanitize(WebTicket webTicket) {
        if(webTicket.getEmail() == null){ webTicket.setEmail(""); }
        else {webTicket.setEmail(webTicket.getEmail().trim());}

        if(webTicket.getClassifier() == null){ webTicket.setClassifier(""); }
        else {webTicket.setClassifier(webTicket.getClassifier().trim());}

        if(webTicket.getSubject() == null){ webTicket.setSubject(""); }
        else {webTicket.setSubject(webTicket.getSubject().trim());}

        if(webTicket.getNote() == null){ webTicket.setNote(""); }
        else {webTicket.setNote(webTicket.getNote().trim());}
        return webTicket;
    }
}
