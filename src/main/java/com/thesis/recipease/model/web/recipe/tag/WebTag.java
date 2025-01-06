package com.thesis.recipease.model.web.recipe.tag;

public class WebTag {
    String field;

    public WebTag(){}
    public WebTag(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "WebTag{" +
                "field='" + field + '\'' +
                '}';
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
        return (this.field == null && otherTag.field == null) || (this.field != null && this.field.equals(otherTag.field));
    }

    //needed to easily delete duplicates
    @Override
    public int hashCode() {
        if (field == null){
            return 0;
        }
        else{
            return field.hashCode();
        }
    }
}
