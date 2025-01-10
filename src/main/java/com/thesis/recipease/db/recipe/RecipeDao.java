package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.recipe.component.Recipe;
import com.thesis.recipease.model.recipe.component.RecipeInfo;
import com.thesis.recipease.model.recipe.engagement.RecipeComment;
import com.thesis.recipease.model.web.recipe.component.WebRecipe;
import com.thesis.recipease.model.web.recipe.engagement.WebComment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface RecipeDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Recipe addRecipe(int userId, WebRecipe webRecipe);
    public RecipeComment addComment(int userId, int recipeId, WebComment webComment);
    // READ OPS
    public Recipe getRecipeById(int recipeId);
    public List<RecipeInfo> getRecipesByUserId(int userId);
    // UPDATE OPS
    // DELETE OPS
}
