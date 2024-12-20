package com.thesis.recipease.db;

import com.thesis.recipease.db.account.AccountDao;
import com.thesis.recipease.db.profile.ProfileDao;
import com.thesis.recipease.db.glossary.GlossaryDao;
import com.thesis.recipease.db.recipe.RecipeDao;
import com.thesis.recipease.db.substitution.SubstitutionDao;
import com.thesis.recipease.model.*;
import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.web.WebAccount;
import com.thesis.recipease.model.web.WebProfile;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private GlossaryDao glossaryDao;
    @Autowired
    private ProfileDao profileDao;
    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private SubstitutionDao substitutionDao;

    // ------------------------
    // ACCOUNT OPERATIONS
    // ------------------------

    // CREATE OPS
    public Account addAccount(WebAccount webAccount, List<String> roles, WebProfile webProfile) { return accountDao.addAccount(webAccount, roles, webProfile); }

    // READ OPS
    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email);
    }
    public Integer getActivationCodeById(int id) { return accountDao.getActivationCodeById(id); }
    public String getPasswordById(int id){
        return accountDao.getPasswordById(id);
    }

    // UPDATE OPS
    public boolean verifyActivationCodeAndActivate(int id, int code, int activationCode) { return accountDao.verifyActivationCodeAndActivate(id, code, activationCode); }
    public Account updateEmailById(int id, String newEmail){ return accountDao.updateEmailById(id, newEmail); }
    public Account updatePasswordById(int id, String password){ return accountDao.updatePasswordById(id, password); }


    // DELETE OPS
    public Account deleteAccountById(int id){
        return accountDao.deleteAccountById(id);
    }
    // ------------------------
    // PROFILE OPERATIONS
    // ------------------------

    // CREATE OPS
    // READ OPS

    public Profile getProfileById(int id) {
        return profileDao.getProfileById(id);
    }

    // UPDATE OPS
    public Profile updateProfile(WebProfile webProfile) {
        return profileDao.updateProfile(webProfile);
    }

    // DELETE OPS

    // ------------------------
    // RECIPE OPERATIONS
    // ------------------------

    // CREATE OPS
    public Recipe addRecipe(int userId, WebRecipe webRecipe){ return recipeDao.addRecipe(userId, webRecipe); }
    // READ OPS
    public Recipe getRecipeById(int recipeId) { return recipeDao.getRecipeById(recipeId); }
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


    // ------------------------------------------------
    // HELPER METHODS
    // ------------------------------------------------
    public int getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return Math.toIntExact(userDetails.getUserId()); // Safely convert Long to int
        }
        throw new IllegalStateException("User is not authenticated or user details are invalid");
    }
}
