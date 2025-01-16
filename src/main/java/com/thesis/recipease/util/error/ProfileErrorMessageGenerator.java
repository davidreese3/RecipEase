package com.thesis.recipease.util.error;

import org.springframework.stereotype.Service;

@Service
public class ProfileErrorMessageGenerator {
    private String [] profileErrors = {
            "Profile Not Found! Did it go on vacation? Let’s find someone else!",
            "Oops! This profile is playing hide and seek. Try another chef.",
            "This chef must be undercover. Their profile is nowhere to be found!",
            "Uh-oh, it seems like this profile has vanished into thin air. How about checking another one?",
            "404: Profile Not Found. Maybe it’s time to meet some new chefs!",
            "No profile found! Maybe it’s off on a culinary adventure?",
            "Profile not found! Perhaps it’s simmering somewhere else.",
            "Looks like this chef's profile is missing from the menu. Try searching for a different chef!"
    };

    public String getProfileError(){
        return profileErrors[(int) Math.floor(Math.random() * 7)];
    }

    public String getPersonalProfileError(){
        return "You do not have a profile created.";
    }
}
