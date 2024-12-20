package com.thesis.recipease.model.web.recipe;

public class WebDirection {
    private String direction;
    private String method;
    private Integer temp;
    private String level;

    public WebDirection(){}

    public WebDirection(String direction, String method, Integer temp, String level) {
        this.direction = direction;
        this.method = method;
        this.temp = temp;
        this.level = level;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "WebDirection{" +
                ", direction='" + direction + '\'' +
                ", method='" + method + '\'' +
                ", temp=" + temp +
                ", level='" + level + '\'' +
                '}';
    }
}
