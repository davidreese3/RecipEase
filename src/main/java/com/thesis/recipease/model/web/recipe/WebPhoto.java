package com.thesis.recipease.model.web.recipe;

import org.springframework.web.multipart.MultipartFile;

public class WebPhoto {
    private String fileName;
    private MultipartFile file;

    public WebPhoto(){}

    public WebPhoto(String fileName, MultipartFile file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
