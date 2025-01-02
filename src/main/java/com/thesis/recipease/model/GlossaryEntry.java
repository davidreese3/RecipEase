package com.thesis.recipease.model;


import java.util.Arrays;
import java.util.List;

public class GlossaryEntry {
    public String term;
    public String definition;
    public String [] relatedTerms;

    public GlossaryEntry(){}

    public GlossaryEntry(String term, String definition, String [] relatedTerms) {
        this.term = term;
        this.definition = definition;
        this.relatedTerms = relatedTerms;
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

    public String [] getRelatedTerms() {
        return relatedTerms;
    }

    public void setRelatedTerms(String [] relatedTerms) {
        this.relatedTerms = relatedTerms;
    }

    @Override
    public String toString() {
        return "GlossaryEntry{" +
                "term='" + term + '\'' +
                ", definition='" + definition + '\'' +
                ", relatedTerms=" + Arrays.toString(relatedTerms) +
                '}';
    }
}