package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.recipe.Recipe;
import com.thesis.recipease.model.recipe.RecipeDirection;
import com.thesis.recipease.model.recipe.RecipeInfo;
import com.thesis.recipease.model.recipe.RecipeIngredient;
import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class RecipeDaoImpl implements RecipeDao{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;


    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }


    // ------------------------------------------------
    // CREATE OPS
    // ------------------------------------------------
    @Override
    @Transactional
    public Recipe addRecipe(int userId, WebRecipe webRecipe) {
        int recipeId;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            final String infoSQL = "insert into info (userid, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(infoSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, webRecipe.getName());
                ps.setString(3, webRecipe.getDescription());
                ps.setDouble(4, webRecipe.getYield());
                ps.setString(5, webRecipe.getUnitOfYield());
                ps.setInt(6, webRecipe.getPrepMin());
                ps.setInt(7, webRecipe.getPrepHr());
                ps.setInt(8, webRecipe.getProcessMin());
                ps.setInt(9, webRecipe.getProcessHr());
                ps.setInt(10, webRecipe.getTotalMin());
                ps.setInt(11, webRecipe.getTotalHr());
                return ps;
            }, keyHolder);
            List<Map<String, Object>> keylist= keyHolder.getKeyList();
            if (!keyHolder.getKeyList().isEmpty()) {
                Map<String, Object> keyMap = keyHolder.getKeyList().get(0); // Get the first key map
                recipeId = (int) keyMap.get("recipeid"); // Fetch the 'recipeid' field
            } else {
                throw new IllegalStateException("Failed to retrieve the generated recipe ID.");
            }
            final String ingredientSQL = "insert into ingredient (recipeid, component, wholeNumberQuantity, fractionQuantity, measurement, preparation) values (?, ?, ?, ?, ?, ?)";
            List<WebIngredient> webIngredients = webRecipe.getIngredients();
            for(WebIngredient webIngredient : webIngredients){
                System.out.println("inserting ingredient");
                jdbcTemplate.update(dataSource -> {
                    PreparedStatement ps = dataSource.prepareStatement(ingredientSQL);
                    ps.setInt(1, recipeId);
                    ps.setString(2, webIngredient.getComponent());
                    ps.setDouble(3, webIngredient.getWholeNumberQuantity());
                    ps.setString(4, webIngredient.getFractionQuantity());
                    ps.setString(5, webIngredient.getMeasurement());
                    ps.setString(6, webIngredient.getPreparation());
                    return ps;
                });
            }
            int stepNum = 1;
            final String directionSQL = "insert into direction (recipeid, stepNum, direction, method, temp, level) values (?, ?, ?, ?, ?, ?)";
            List<WebDirection> webDirections = webRecipe.getDirections();
            for(WebDirection webDirection : webDirections){
                System.out.println("inserting direction");
                int finalStepNum = stepNum;
                jdbcTemplate.update(dataSource -> {
                    PreparedStatement ps = dataSource.prepareStatement(directionSQL);
                    ps.setInt(1, recipeId);
                    ps.setInt(2, finalStepNum);
                    ps.setString(3, webDirection.getDirection());
                    ps.setString(4, webDirection.getMethod());
                    ps.setDouble(5, webDirection.getTemp());
                    ps.setString(6, webDirection.getLevel());
                    return ps;
                });
                stepNum++;
            }
            transactionManager.commit(status);
            System.out.println("Success");
        }
        catch (DataAccessException e) {
            System.out.println("Error in adding recipe");
            transactionManager.rollback(status);
            throw e;
        }
        return getRecipeById(recipeId);
    }


    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    @Override
    public Recipe getRecipeById(int recipeId){
        final String infoSQL = "select * from info where recipeid = ?";
        RecipeInfo recipeInfo = new RecipeInfo();
        try {
            recipeInfo =jdbcTemplate.queryForObject(infoSQL, new RecipeDaoImpl.RecipeInfoMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            recipeInfo = null;
        }
        List<RecipeIngredient> recipeIngredients;
        final String ingredientSQL = "select * from ingredient where recipeid = ?";
        try {
            recipeIngredients = jdbcTemplate.query(ingredientSQL, new RecipeDaoImpl.RecipeIngredientMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            recipeIngredients = null;
        }
        List<RecipeDirection> recipeDirections;
        final String directionSQL = "select * from direction where recipeid = ?";
        try {
            recipeDirections = jdbcTemplate.query(directionSQL, new RecipeDaoImpl.RecipeDirectionMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            recipeDirections = null;
        }
        return new Recipe(recipeInfo, recipeIngredients, recipeDirections);
    }

    // ------------------------------------------------
    // UPDATE OPS
    // ------------------------------------------------

    // ------------------------------------------------
    // DELETE OPS
    // ------------------------------------------------

    // ------------------------------------------------
    // MAPPERS
    // ------------------------------------------------
    class RecipeInfoMapper implements RowMapper<RecipeInfo> {
        @Override
        public RecipeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeInfo recipeInfo = new RecipeInfo();
            recipeInfo.setRecipeId(rs.getInt("recipeId"));
            recipeInfo.setUserId(rs.getInt("userId"));
            recipeInfo.setName(rs.getString("name"));
            recipeInfo.setDescription(rs.getString("description"));
            recipeInfo.setYield(rs.getDouble("yield"));
            recipeInfo.setUnitOfYield(rs.getString("unitofyield"));
            recipeInfo.setPrepMin(rs.getInt("prepMin"));
            recipeInfo.setPrepHr(rs.getInt("prepHr"));
            recipeInfo.setProcessMin(rs.getInt("processMin"));
            recipeInfo.setProcessHr(rs.getInt("processHr"));
            recipeInfo.setTotalMin(rs.getInt("totalMin"));
            recipeInfo.setTotalHr(rs.getInt("totalHr"));
            return recipeInfo;
        }
    }

    class RecipeIngredientMapper implements RowMapper<RecipeIngredient> {
        @Override
        public RecipeIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipeId(rs.getInt("recipeId"));
            recipeIngredient.setComponent(rs.getString("component"));
            recipeIngredient.setWholeNumberQuantity(rs.getInt("wholeNumberQuantity"));
            recipeIngredient.setFractionQuantity(rs.getString("fractionQuantity"));
            recipeIngredient.setMeasurement(rs.getString("measurement"));
            recipeIngredient.setPreparation(rs.getString("preparation"));
            return recipeIngredient;
        }
    }

    class RecipeDirectionMapper implements RowMapper<RecipeDirection> {
        @Override
        public RecipeDirection mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeDirection recipeDirection = new RecipeDirection();
            recipeDirection.setRecipeId(rs.getInt("recipeId"));
            recipeDirection.setStepNum(rs.getInt("stepNum"));
            recipeDirection.setDirection(rs.getString("direction"));
            recipeDirection.setMethod(rs.getString("method"));
            recipeDirection.setTemp(rs.getInt("temp"));
            recipeDirection.setLevel(rs.getString("level"));
            return recipeDirection;
        }
    }
}
