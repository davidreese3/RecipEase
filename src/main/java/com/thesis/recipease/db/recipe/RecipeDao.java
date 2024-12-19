package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.Recipe;
import com.thesis.recipease.model.SubstitutionEntry;
import com.thesis.recipease.model.web.WebRecipeInfo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface RecipeDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Recipe addRecipe(String email, WebRecipeInfo webRecipeInfo);
    // READ OPS
    public Recipe getRecipeInfoById(int id);
    // UPDATE OPS
    // DELETE OPS
}
