package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.domain.recipe.Recipe;
import com.thesis.recipease.model.domain.recipe.RecipeInfo;
import com.thesis.recipease.model.domain.recipe.RecipeComment;
import com.thesis.recipease.model.domain.recipe.RecipeRating;
import com.thesis.recipease.model.web.recipe.WebRating;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebComment;
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
    // READ OPS
    public Recipe getRecipeById(int recipeId);
    public List<RecipeInfo> getRecipesByUserId(int userId);
    public RecipeInfo getRecipeInfoById(int recipeId);
    // UPDATE OPS
    // DELETE OPS
}
