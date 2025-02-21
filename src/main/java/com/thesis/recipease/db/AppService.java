package com.thesis.recipease.db;

import com.thesis.recipease.db.account.AccountDao;
import com.thesis.recipease.db.profile.ProfileDao;
import com.thesis.recipease.db.glossary.GlossaryDao;
import com.thesis.recipease.db.recipe.RecipeDao;
import com.thesis.recipease.db.substitution.SubstitutionDao;
import com.thesis.recipease.model.domain.glossary.GlossaryEntry;
import com.thesis.recipease.model.domain.recipe.Recipe;
import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.domain.profile.Profile;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import com.thesis.recipease.model.domain.recipe.RecipeComment;
import com.thesis.recipease.model.domain.recipe.RecipeRating;
import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import com.thesis.recipease.model.user.User;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.profile.WebProfile;
import com.thesis.recipease.model.web.recipe.util.WebRating;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebComment;
import com.thesis.recipease.model.web.recipe.util.WebSearch;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Account getAccountById(int id) { return  accountDao.getAccountById(id); }
    public Integer getVerificationCodeById(int id) { return accountDao.getVerificationCodeById(id); }
    public String getPasswordById(int id){
        return accountDao.getPasswordById(id);
    }
    public List<User> getUsers() { return accountDao.getUsers(); }

    // UPDATE OPS
    public boolean verifyVerificationCodeAndActivate(int id, int code, int verificationCode) { return accountDao.verifyVerificationCodeAndActivate(id, code, verificationCode); }
    public Account updateEmailById(int id, String newEmail){ return accountDao.updateEmailById(id, newEmail); }
    public Account updatePasswordById(int id, String password){ return accountDao.updatePasswordById(id, password); }
    public Account deactivateAccountById(int id) { return accountDao.deactivateAccountById(id); }
    public int generateAndSaveVerificationCode(int id) { return accountDao.generateAndSaveVerificationCode(id); }
    public int reactivateAccountById(int id) { return accountDao.reactivateAccountById(id); }

    // DELETE OPS
    public int deleteAccountById(int id){
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
    public String getNameById(int id) {
        return profileDao.getNameById(id);
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
    public RecipeComment addComment(int userId, int recipeId, WebComment webComment){ return recipeDao.addComment(userId, recipeId, webComment); }
    public RecipeRating addRating(int userId, int recipeId, WebRating webRating) {return recipeDao.addRating(userId, recipeId, webRating); }

    // READ OPS
    public Recipe getRecipeById(int recipeId) { return recipeDao.getRecipeById(recipeId); }
    public List<RecipeInfo> getRecipesByUserId(int userId) { return recipeDao.getRecipesByUserId(userId); }
    public int getUserIdByRecipeId(int recipeId) { return recipeDao.getUserIdByRecipeId(recipeId); }
    public WebRecipe getWebRecipeById(int recipeId) { return recipeDao.getWebRecipeById(recipeId); }
    public int getNumberOfVariationByOriginalRecipeId(int originalRecipeId) { return recipeDao.getNumberOfVariationByOriginalRecipeId(originalRecipeId); };
    public int getDepthOfVariationByOriginalRecipeId(int originalRecipeId) { return recipeDao.getDepthOfVariationByOriginalRecipeId(originalRecipeId); }
    public List<RecipeInfo> getRecipesBySearchCriteria(WebSearch webSearch){ return recipeDao.getRecipesBySearchCriteria(webSearch); }
    public List<RecipeInfo> getRecipes() { return recipeDao.getRecipes(); }
    public List<RecipeInfo> getCommunityTrendingRecipes() { return recipeDao.getCommunityTrendingRecipes(); }

    // UPDATE OPS
    // DELETE OPS
    public int deleteRecipeByRecipeId(int recipeId) { return recipeDao.deleteRecipeByRecipeId(recipeId); }
    public int deleteCommentByCommentId(int commentId) { return recipeDao.deleteCommentByCommentId(commentId); }


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
