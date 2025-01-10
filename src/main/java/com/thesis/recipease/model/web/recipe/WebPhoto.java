package com.thesis.recipease.model.web.recipe;

public class WebPhoto {
    private String fileName;

    public WebPhoto(){}

    public WebPhoto(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
