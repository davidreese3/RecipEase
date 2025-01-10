package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.recipe.RecipeInfo;
import com.thesis.recipease.model.recipe.engagement.Comment;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.engagement.WebComment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface RecipeDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Recipe addRecipe(int userId, WebRecipe webRecipe);
    public Comment addComment(int userId, int recipeId, WebComment webComment);
    // READ OPS
    public Recipe getRecipeById(int recipeId);
    public List<RecipeInfo> getRecipesByUserId(int userId);
    public List<Comment> getCommentsByRecipeId(int recipeId);
    // UPDATE OPS
    // DELETE OPS
}
