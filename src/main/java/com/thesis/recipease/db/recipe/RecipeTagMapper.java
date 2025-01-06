package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.recipe.tag.RecipeTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeTagMapper implements RowMapper<RecipeTag> {
    private final String field;

    public RecipeTagMapper(String field){
        this.field = field;
    }

    @Override
    public RecipeTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecipeTag recipeTag = new RecipeTag();
        recipeTag.setRecipeId(rs.getInt("recipeId"));
        recipeTag.setField(rs.getString(field));
        return recipeTag;
    }

}
