package com.thesis.recipease.util.generator.profile;

import com.thesis.recipease.util.generator.ErrorMessageGenerator;
import org.springframework.stereotype.Service;

@Service
public class ProfileDeactivatedErrorGenerator extends ErrorMessageGenerator {
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

    protected ProfileDeactivatedErrorGenerator() {
        super(DEACTIVATED_PROFILE_ERRORS);
    }
}
