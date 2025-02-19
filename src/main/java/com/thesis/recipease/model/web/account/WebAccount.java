package com.thesis.recipease.model.web.account;

public class WebAccount {
    private String email;
    private String password;
    private String confirmPassword;
    private int verificationCode;

    public WebAccount(){}

    public WebAccount(String email, String password, String confirmPassword, int verificationCode) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }
}
