package com.thesis.recipease.db;

import com.thesis.recipease.db.account.AccountDao;
import com.thesis.recipease.db.profile.ProfileDao;
import com.thesis.recipease.db.glossary.GlossaryDao;
import com.thesis.recipease.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ProfileDao profileDao;
    @Autowired
    private GlossaryDao glossaryDao;

    // ------------------------
    // ACCOUNT OPERATIONS
    // ------------------------

    // CREATE OPS
    public Account addAccount(WebAccount webAccount, List<String> roles, WebProfile webProfile) {
        return accountDao.addAccount(webAccount, roles, webProfile);
    }

    // READ OPS
    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email);
    }

    public Integer getActivationCodeById(String email) {
        return accountDao.getActivationCodeByEmail(email);
    }

    // UPDATE OPS
    public boolean verifyActivationCodeAndActivate(String email, int code) {
        return accountDao.verifyActivationCodeAndActivate(email, code);
    }

    public String getPasswordByEmail(String email){
        return accountDao.getPasswordByEmail(email);
    }

    // DELETE OPS

    // ------------------------
    // PROFILE OPERATIONS
    // ------------------------

    // CREATE OPS
    // READ OPS

    public Profile getProfileByEmail(String email) {
        return profileDao.getProfileByEmail(email);
    }

    // UPDATE OPS
    public WebProfile updateProfile(WebProfile webProfile) {
        return profileDao.updateProfile(webProfile);
    }

    // DELETE OPS

    // ------------------------
    // RECIPE OPERATIONS
    // ------------------------

    // CREATE OPS
    // READ OPS
    // UPDATE OPS
    // DELETE OPS

    // ------------------------
    // GLOSSARY OPERATIONS
    // ------------------------

    // CREATE OPS
    // READ OPS
    public List<GlossaryEntry> getAllGlossaryEntries(){
        return glossaryDao.getAllGlossaryEntries();
    }
    // UPDATE OPS
    // DELETE OPS

    // ------------------------
    // SUBSTITUTIONS OPERATIONS
    // ------------------------

    // CREATE OPS
    // READ OPS
    // UPDATE OPS
    // DELETE OPS
}
