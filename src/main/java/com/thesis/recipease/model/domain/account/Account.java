package com.thesis.recipease.model.domain.account;

public class Account {
    private int id;
    private String email;
    private String password;
    private boolean active;
    private int verificationCode;

    public Account(){}
    public Account(int id, String email, String password, boolean active, int verificationCode){
        this.id = id;
        this.email = email;
        this.password = password;
        this.active = active;
        this.verificationCode = verificationCode;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }
}
