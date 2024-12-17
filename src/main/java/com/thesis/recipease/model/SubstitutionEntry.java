package com.thesis.recipease.model;

public class SubstitutionEntry {
    private String originalComponent;
    private int originalQuantity;
    private String originalMeasurement;
    private String originalPreparation;
    private String substitutedComponent;
    private int substitutedQuantity;
    private String substitutedMeasurement;
    private String substitutedPreparation;

    public SubstitutionEntry(){}
    public SubstitutionEntry(String originalComponent, int originalQuantity, String originalMeasurement, String originalPreparation, String substitutedComponent, int substitutedQuantity, String substitutedMeasurement, String substitutedPreparation) {
        this.originalComponent = originalComponent;
        this.originalQuantity = originalQuantity;
        this.originalMeasurement = originalMeasurement;
        this.originalPreparation = originalPreparation;
        this.substitutedComponent = substitutedComponent;
        this.substitutedQuantity = substitutedQuantity;
        this.substitutedMeasurement = substitutedMeasurement;
        this.substitutedPreparation = substitutedPreparation;
    }

    public String getOriginalComponent() {
        return originalComponent;
    }

    public void setOriginalComponent(String originalComponent) {
        this.originalComponent = originalComponent;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(int originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public String getOriginalMeasurement() {
        return originalMeasurement;
    }

    public void setOriginalMeasurement(String originalMeasurement) {
        this.originalMeasurement = originalMeasurement;
    }

    public String getOriginalPreparation() {
        return originalPreparation;
    }

    public void setOriginalPreparation(String originalPreparation) {
        this.originalPreparation = originalPreparation;
    }

    public String getSubstitutedComponent() {
        return substitutedComponent;
    }

    public void setSubstitutedComponent(String substitutedComponent) {
        this.substitutedComponent = substitutedComponent;
    }

    public int getSubstitutedQuantity() {
        return substitutedQuantity;
    }

    public void setSubstitutedQuantity(int substitutedQuantity) {
        this.substitutedQuantity = substitutedQuantity;
    }

    public String getSubstitutedMeasurement() {
        return substitutedMeasurement;
    }

    public void setSubstitutedMeasurement(String substitutedMeasurement) {
        this.substitutedMeasurement = substitutedMeasurement;
    }

    public String getSubstitutedPreparation() {
        return substitutedPreparation;
    }

    public void setSubstitutedPreparation(String substitutedPreparation) {
        this.substitutedPreparation = substitutedPreparation;
    }
}
