package com.thesis.recipease.model.web.util;

public class WebTicket {
    private String classifier;
    private String email;
    private String subject;
    private String note;

    public WebTicket(){}

    public WebTicket(String classifier, String email, String subject, String note) {
        this.classifier = classifier;
        this.email = email;
        this.subject = subject;
        this.note = note;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
