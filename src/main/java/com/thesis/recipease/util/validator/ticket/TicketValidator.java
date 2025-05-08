package com.thesis.recipease.util.validator.ticket;

import com.thesis.recipease.model.web.ticket.WebTicket;
import com.thesis.recipease.util.validator.Validator;
import com.thesis.recipease.util.validator.util.DropdownValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TicketValidator implements Validator<WebTicket, ArrayList<String>> {

    @Autowired
    private DropdownValidator dropdownValidator;

    public ArrayList<String> validate(WebTicket webTicket) {
        ArrayList<String> errors = new ArrayList<>();

        if(!isEmailValid(webTicket.getEmail())) {
            errors.add("Invalid email address.");
        }
        if (!isFieldValid(webTicket.getEmail())) {
            errors.add("Email cannot be blank.");
        } else if (!isFieldLengthValid(webTicket.getEmail(), 50)) {
            errors.add("Email cannot be longer than 50 characters.");
        }

        if (!dropdownValidator.isValidClassifier(webTicket.getClassifier())) {
            errors.add("Classifier is not a valid selection.");
        }

        if (!isFieldValid(webTicket.getSubject())) {
            errors.add("Subject cannot be blank.");
        } else if (!isFieldLengthValid(webTicket.getSubject(), 40)) {
            errors.add("Subject cannot be longer than 40 characters.");
        }

        if (!isFieldValid(webTicket.getNote())) {
            errors.add("Note cannot be blank.");
        } else if (!isFieldLengthValid(webTicket.getNote(), 280)) {
            errors.add("Note cannot be longer than 280 characters.");
        }

        return errors;
    }

    private boolean isFieldValid(String field) {return field != null && !field.trim().isEmpty();}
    private boolean isFieldLengthValid(String field, int limit) {return field.length() <= limit;}

    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
