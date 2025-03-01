package com.thesis.recipease.util.generator;

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

    private static final String [] DEACTIVATED_PROFILE_ERRORS = {
            "This chef has hung up their apron! While they are no longer active and sharing recipes, you can still savor the history of their culinary creations.",
            "This profile has been whisked away! Looks like they’re taking a break from the kitchen.",
            "Out of the frying pan and into retirement! This profile is no longer active.",
            "This chef has flipped their last pancake... for now! Check out other delicious profiles instead.",
            "Closed for renovations! This chef’s profile is temporarily out of service.",
            "Vanished like a soufflé in a strong breeze! This chef is no longer active.",
            "Looks like this chef has packed up their spices and sailed off into the sunset!",
            "No longer in the kitchen, but their legacy still smells amazing!",
            "This profile has been gently simmered into retirement. Try another one!",
            "Chef’s gone fishing! No new recipes, but you can enjoy their past creations.",
    };

    public static String getProfileError(){
        return PROFILE_ERRORS[(int) Math.floor(Math.random() * PROFILE_ERRORS.length)];
    }

    public static String getPersonalProfileError(){
        return "You do not have a profile created.";
    }

    public static String getDeactivatedProfileError(){
        return DEACTIVATED_PROFILE_ERRORS[(int) Math.floor(Math.random() * DEACTIVATED_PROFILE_ERRORS.length)];
    }
}
