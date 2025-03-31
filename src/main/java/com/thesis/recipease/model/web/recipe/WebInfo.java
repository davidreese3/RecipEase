package com.thesis.recipease.model.web.recipe;

public class WebInfo {
    private String name;
    private String description;
    private String yield;
    private String unitOfYield;
    private Integer prepMin;
    private Integer prepHr;
    private Integer processMin;
    private Integer processHr;
    private int totalMin;
    private int totalHr;

    public WebInfo(){}

    public WebInfo(String name, String description, String yield, String unitOfYield, Integer prepMin, Integer prepHr, Integer processMin, Integer processHr, int totalMin, int totalHr) {
        this.name = name;
        this.description = description;
        this.yield = yield;
        this.unitOfYield = unitOfYield;
        this.prepMin = prepMin;
        this.prepHr = prepHr;
        this.processMin = processMin;
        this.processHr = processHr;
        this.totalMin = totalMin;
        this.totalHr = totalHr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getUnitOfYield() {
        return unitOfYield;
    }

    public void setUnitOfYield(String unitOfYield) {
        this.unitOfYield = unitOfYield;
    }

    public Integer getPrepMin() {
        return prepMin;
    }

    public void setPrepMin(Integer prepMin) {
        this.prepMin = prepMin;
    }

    public Integer getPrepHr() {
        return prepHr;
    }

    public void setPrepHr(Integer prepHr) {
        this.prepHr = prepHr;
    }

    public Integer getProcessMin() {
        return processMin;
    }

    public void setProcessMin(Integer processMin) {
        this.processMin = processMin;
    }

    public Integer getProcessHr() {
        return processHr;
    }

    public void setProcessHr(Integer processHr) {
        this.processHr = processHr;
    }

    public int getTotalMin() {
        return totalMin;
    }

    public void setTotalMin(int totalMin) {
        this.totalMin = totalMin;
    }

    public int getTotalHr() {
        return totalHr;
    }

    public void setTotalHr(int totalHr) {
        this.totalHr = totalHr;
    }
}
