package com.thesis.recipease.model.user;

public class User {
    private int id;
    private String email;
    private boolean active;
    private String name;
    private String roles;

    public User(){}

    public User(int id, String email, boolean active, String name, String roles) {
        this.id = id;
        this.email = email;
        this.active = active;
        this.name = name;
        this.roles = roles;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
