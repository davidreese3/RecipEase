package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.recipe.RecipeInfo;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface RecipeDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Recipe addRecipe(int userId, WebRecipe webRecipe);
    // READ OPS
    public Recipe getRecipeById(int recipeId);
    public List<RecipeInfo> getRecipeByUserId(int userId);
    // UPDATE OPS
    // DELETE OPS
}
