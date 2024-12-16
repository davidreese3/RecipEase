package com.thesis.recipease.model;


public class GlossaryEntry {
    public String term;
    public String definition;

    public GlossaryEntry(){}

    public GlossaryEntry(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
