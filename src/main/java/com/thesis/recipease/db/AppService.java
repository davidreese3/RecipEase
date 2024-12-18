package com.thesis.recipease.db;

import com.thesis.recipease.db.account.AccountDao;
import com.thesis.recipease.db.profile.ProfileDao;
import com.thesis.recipease.db.glossary.GlossaryDao;
import com.thesis.recipease.db.substitution.SubstitutionDao;
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
    @Autowired
    private SubstitutionDao substitutionDao;

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

    public Integer getActivationCodeByEmail(String email) {
        return accountDao.getActivationCodeByEmail(email);
    }

    public String getPasswordByEmail(String email){
        return accountDao.getPasswordByEmail(email);
    }

    // UPDATE OPS
    public boolean verifyActivationCodeAndActivate(String email, int code, int activationCode) {
        return accountDao.verifyActivationCodeAndActivate(email, code, activationCode);
    }

    public String updateEmailByEmail(String originalEmail, String newEmail){
        return accountDao.updateEmailByEmail(originalEmail, newEmail);
    }

    public String updatePasswordByEmail(String email, String password){
        return accountDao.updatePasswordByEmail(email, password);
    }


    // DELETE OPS
    public String deleteAccountByEmail(String email){
        return accountDao.deleteAccountByEmail(email);
    }
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
    public List<SubstitutionEntry> getAllSubstitutionEntries(){
        return substitutionDao.getAllSubstitutionEntries();
    }
    // UPDATE OPS
    // DELETE OPS
}
