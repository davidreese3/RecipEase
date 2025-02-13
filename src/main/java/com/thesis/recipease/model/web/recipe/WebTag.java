package com.thesis.recipease.model.web.recipe;

public class WebTag {
    private String field;
    private String value;

    public WebTag(){}

    public WebTag(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //needed to easily delete duplicates
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebTag)) {
            return false;
        }
        WebTag otherTag = (WebTag) obj;
        return (this.value == null && otherTag.value == null) || (this.value != null && this.value.equals(otherTag.value));
    }

    //needed to easily delete duplicates
    @Override
    public int hashCode() {
        if (value == null){
            return 0;
        }
        else{
            return value.hashCode();
        }
    }
}
