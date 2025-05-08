package com.thesis.recipease.model.domain.ticket;

public class Ticket {
    private int id;
    private String email;
    private String classifier;
    private String subject;
    private String note;
    private boolean solved;

    public Ticket(){}

    public Ticket(int id, String email, String classifier, String subject, String note, boolean solved) {
        this.id = id;
        this.email = email;
        this.classifier = classifier;
        this.subject = subject;
        this.note = note;
        this.solved = solved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
