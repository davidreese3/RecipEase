package com.thesis.recipease.model.domain.recipe;

public class RatingInfo {
    private double averageRating;
    private int numberOfRaters;

    public RatingInfo(){}

    public RatingInfo(double averageRating, int numberOfRaters) {
        this.averageRating = averageRating;
        this.numberOfRaters = numberOfRaters;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getNumberOfRaters() {
        return numberOfRaters;
    }

    public void setNumberOfRaters(int numberOfRaters) {
        this.numberOfRaters = numberOfRaters;
    }
}
