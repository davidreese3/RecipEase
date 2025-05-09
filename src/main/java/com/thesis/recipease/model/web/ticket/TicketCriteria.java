package com.thesis.recipease.model.web.ticket;

public class TicketCriteria {
    private String classifier;
    private String solved;

    public TicketCriteria() {}

    public TicketCriteria(String classifier, String solved) {
        this.classifier = classifier;
        this.solved = solved;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getSolved() {
        return solved;
    }

    public void setSolved(String solved) {
        this.solved = solved;
    }
}
