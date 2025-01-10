package com.thesis.recipease.model.domain.form;

import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.profile.WebProfile;

public class RegistrationForm {
    private WebAccount webAccount;
    private WebProfile webProfile;

    public RegistrationForm(){}

    public WebAccount getWebAccount() {
        return webAccount;
    }

    public void setWebAccount(WebAccount webAccount) {
        this.webAccount = webAccount;
    }

    public WebProfile getWebProfile() {
        return webProfile;
    }

    public void setWebProfile(WebProfile webProfile) {
        this.webProfile = webProfile;
    }
}
