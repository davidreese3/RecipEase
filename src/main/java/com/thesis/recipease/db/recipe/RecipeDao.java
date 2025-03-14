package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.domain.recipe.Recipe;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import com.thesis.recipease.model.domain.recipe.util.RecipeComment;
import com.thesis.recipease.model.domain.recipe.util.RecipeRating;
import com.thesis.recipease.model.web.recipe.util.WebRating;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.util.WebComment;
import com.thesis.recipease.model.web.recipe.util.WebSearch;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface RecipeDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Recipe addRecipe(int userId, WebRecipe webRecipe);
    public RecipeComment addComment(int userId, int recipeId, WebComment webComment);
    public RecipeRating addRating(int userId, int recipeId, WebRating webRating);
    public int addRecipeToBookmark(int userId, int recipeId);

    // READ OPS
    public Recipe getRecipeById(int recipeId);
    public WebRecipe getWebRecipeById(int recipeId);
    public List<RecipeInfo> getRecipesByUserId(int userId);
    public int getUserIdByRecipeId(int recipeId);
    public int getNumberOfVariationByOriginalRecipeId(int originalRecipeId);
    public int getDepthOfVariationByOriginalRecipeId(int originalRecipeId);
    public List<RecipeInfo> getRecipesBySearchCriteria(WebSearch webSearch);
    public List<RecipeInfo> getRecipes();
    public List<RecipeInfo> getCommunityTrendingRecipes();
    public List<RecipeInfo> getStaffTrendingRecipes();
    public List<RecipeInfo> getBookmarksByUserId(int userId);
    // UPDATE OPS
    public int addRecipeToTrendingById(int recipeId);
    public int removeRecipeFromTrendingById(int recipeId);
    // DELETE OPS
    public int deleteRecipeByRecipeId(int recipeId);
    public int deleteCommentByCommentId(int commentId);
    public int deleteRecipeFromBookmark(int userId, int recipeId);
}
