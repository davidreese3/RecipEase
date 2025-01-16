package com.thesis.recipease.util.error;

import org.springframework.stereotype.Service;

@Service
public class ProfileErrorMessageGenerator {
    private static final String [] PROFILE_ERRORS = {
            "Profile Not Found! Did it go on vacation? Let’s find someone else!",
            "Oops! This profile is playing hide and seek. Try another chef.",
            "This chef must be undercover. Their profile is nowhere to be found!",
            "Uh-oh, it seems like this profile has vanished into thin air. How about checking another one?",
            "No profile found! Maybe it’s off on a culinary adventure?",
            "Profile not found! Perhaps it’s simmering somewhere else.",
            "Looks like this chef's profile is missing from the menu. Try searching for a different chef!"
    };

    public String getProfileError(){
        return PROFILE_ERRORS[(int) Math.floor(Math.random() * PROFILE_ERRORS.length)];
    }

    public String getPersonalProfileError(){
        return "You do not have a profile created.";
    }

    public String getDeactivatedProfileError(){
        return "This chef has hung up their apron! While they are longer active and sharing recipes, you can still savor the history of their culinary creations.";
    }
}
