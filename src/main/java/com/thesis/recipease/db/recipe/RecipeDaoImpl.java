package com.thesis.recipease.db.recipe;

import com.thesis.recipease.db.profile.ProfileDaoImpl;
import com.thesis.recipease.db.substitution.SubstitutionDaoImpl;
import com.thesis.recipease.model.Recipe;
import com.thesis.recipease.model.SubstitutionEntry;
import com.thesis.recipease.model.web.WebRecipeInfo;
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
    public Recipe addRecipe(String email, WebRecipeInfo webRecipeInfo) {
        int id;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            final String SQL = "insert into info (email, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, webRecipeInfo.getName());
                ps.setString(3, webRecipeInfo.getDescription());
                ps.setDouble(4, webRecipeInfo.getYield());
                ps.setString(5, webRecipeInfo.getUnitOfYield());
                ps.setInt(6, webRecipeInfo.getPrepMin());
                ps.setInt(7, webRecipeInfo.getPrepHr());
                ps.setInt(8, webRecipeInfo.getProcessMin());
                ps.setInt(9, webRecipeInfo.getProcessHr());
                ps.setInt(10, (webRecipeInfo.getPrepMin() + webRecipeInfo.getProcessMin()) % 60);
                ps.setInt(11, webRecipeInfo.getPrepHr() + webRecipeInfo.getProcessHr() + ((webRecipeInfo.getPrepMin() + webRecipeInfo.getProcessMin()) / 60));
                return ps;
            }, keyHolder);
            List<Map<String, Object>> keylist= keyHolder.getKeyList();
            if (!keyHolder.getKeyList().isEmpty()) {
                Map<String, Object> keyMap = keyHolder.getKeyList().get(0); // Get the first key map
                id = (int) keyMap.get("recipeid"); // Fetch the 'recipeid' field
            } else {
                throw new IllegalStateException("Failed to retrieve the generated recipe ID.");
            }
            transactionManager.commit(status);
        }
        catch (DataAccessException e) {
            System.out.println("Error in adding recipe");
            transactionManager.rollback(status);
            throw e;
        }
        return getRecipeInfoById(id);
    }


    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    @Override
    public Recipe getRecipeInfoById(int id){
        final String SQL = "select * from info where recipeid = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new RecipeDaoImpl.RecipeMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
    class RecipeMapper implements RowMapper<Recipe> {
        @Override
        public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
            Recipe recipe = new Recipe();
            recipe.setId(rs.getInt("recipeId"));
            recipe.setEmail(rs.getString("email"));
            recipe.setName(rs.getString("name"));
            recipe.setDescription(rs.getString("description"));
            recipe.setPrepMin(rs.getInt("prepMin"));
            recipe.setPrepHr(rs.getInt("prepHr"));
            recipe.setProcessMin(rs.getInt("processMin"));
            recipe.setProcessHr(rs.getInt("processHr"));
            recipe.setTotalMin(rs.getInt("totalMin"));
            recipe.setTotalHr(rs.getInt("totalHr"));
            return recipe;
        }
    }
}
