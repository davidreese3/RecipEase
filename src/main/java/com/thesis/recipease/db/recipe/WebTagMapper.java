package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.domain.recipe.RecipeTag;
import com.thesis.recipease.model.web.recipe.WebTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WebTagMapper implements RowMapper<WebTag> {
    private final String field;

    public WebTagMapper(String field){
        this.field = field;
    }

    @Override
    public WebTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        WebTag recipeTag = new WebTag();
//        recipeTag.setRecipeId(rs.getInt("recipeId"));
        recipeTag.setField(rs.getString(field));
        return recipeTag;
    }

}
