package com.thesis.recipease.model.web.ticket;

public class WebTicket {
    private String email;
    private String classifier;
    private String subject;
    private String note;

    public WebTicket(){}

    public WebTicket(String email, String classifier, String subject, String note) {
        this.email = email;
        this.classifier = classifier;
        this.subject = subject;
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
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
