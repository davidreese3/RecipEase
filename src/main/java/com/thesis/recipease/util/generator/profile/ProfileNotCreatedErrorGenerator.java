package com.thesis.recipease.util.generator.profile;

import com.thesis.recipease.util.generator.ErrorMessageGenerator;
import org.springframework.stereotype.Service;

@Service
public class ProfileNotCreatedErrorGenerator extends ErrorMessageGenerator {
    private static final String [] PROFILE_NOT_CREATED_ERRORS = {
            "No profile yet? It’s like a recipe without ingredients. Let’s fix that!",
            "Your profile is still in the oven! Let’s bake it to perfection.",
            "Uh-oh! Looks like you haven’t set up your profile yet. Let’s get cooking!",
            "No profile found! Want to whip one up now?",
            "A chef without a profile? That’s like pasta without sauce! Let’s get you started.",
            "Your profile is missing, but don’t worry, we can create one faster than boiling water!",
            "You haven’t made a profile yet. Shall we cook one up together?",
    };

    protected ProfileNotCreatedErrorGenerator() {
        super(PROFILE_NOT_CREATED_ERRORS);
    }
}
