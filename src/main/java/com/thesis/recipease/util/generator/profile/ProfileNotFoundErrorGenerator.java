package com.thesis.recipease.util.generator.profile;

import com.thesis.recipease.util.generator.ErrorMessageGenerator;
import org.springframework.stereotype.Service;

@Service
public class ProfileNotFoundErrorGenerator extends ErrorMessageGenerator {
    private static final String [] PROFILE_ERRORS = {
            "Profile Not Found! Did it go on vacation? Let’s find someone else!",
            "Oops! This profile is playing hide and seek. Try another chef.",
            "This chef must be undercover. Their profile is nowhere to be found!",
            "Uh-oh, it seems like this profile has vanished into thin air. How about checking another one?",
            "No profile found! Maybe it’s off on a culinary adventure?",
            "Profile not found! Perhaps it’s simmering somewhere else.",
            "Looks like this chef's profile is missing from the menu. Try searching for a different chef!"
    };

    protected ProfileNotFoundErrorGenerator() {
        super(PROFILE_ERRORS);
    }
}
