package com.thesis.recipease.db.recipe;

import com.thesis.recipease.model.domain.recipe.*;
import com.thesis.recipease.model.web.recipe.*;
import com.thesis.recipease.model.web.recipe.WebComment;
import com.thesis.recipease.model.web.recipe.util.WebRating;
import com.thesis.recipease.model.web.recipe.util.WebSearch;
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
import java.util.*;

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
            //insertInfo
            recipeId = insertRecipeInfo(userId,webRecipe.getInfo());
            insertRecipeIngredients(webRecipe.getIngredients(), recipeId);
            insertRecipeDirections(webRecipe.getDirections(), recipeId);
            insertNote(webRecipe.getNote(), recipeId);
            insertLinks(webRecipe.getLinks(), recipeId);
            insertUserSubstitutionEntries(webRecipe.getUserSubstitutionEntries(), recipeId);
            insertPhoto(webRecipe.getPhoto(), recipeId);
            insertTags(webRecipe.getHolidays(), recipeId, "holiday");
            insertTags(webRecipe.getMealTypes(), recipeId, "mealType");
            insertTags(webRecipe.getCuisines(), recipeId, "cuisine");
            insertTags(webRecipe.getAllergens(), recipeId, "allergen");
            insertTags(webRecipe.getDietTypes(), recipeId, "dietType");
            insertTags(webRecipe.getCookingLevels(), recipeId, "cookingLevel");
            insertTags(webRecipe.getCookingStyles(), recipeId, "cookingStyle");
            insertVariation(webRecipe.getVariation(), recipeId);
            transactionManager.commit(status);
            System.out.println("Success");
        }
        catch (DataAccessException e) {
            System.out.println("Error in adding recipe");
            transactionManager.rollback(status);
            return null;
        }
        return getRecipeById(recipeId);
    }

    // HELPER METHODS
    private int insertRecipeInfo(int userId, WebInfo webInfo){
        int recipeId;
        final String SQL = "insert into info (userid, name, description, yield, unitOfYield, prepMin, prepHr, processMin, processHr, totalMin, totalHr) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, webInfo.getName());
            ps.setString(3, webInfo.getDescription());
            ps.setDouble(4, webInfo.getYield());
            ps.setString(5, webInfo.getUnitOfYield());
            ps.setInt(6, webInfo.getPrepMin());
            ps.setInt(7, webInfo.getPrepHr());
            ps.setInt(8, webInfo.getProcessMin());
            ps.setInt(9, webInfo.getProcessHr());
            ps.setInt(10, webInfo.getTotalMin());
            ps.setInt(11, webInfo.getTotalHr());
            return ps;
        }, keyHolder);
        List<Map<String, Object>> keylist= keyHolder.getKeyList();
        if (!keyHolder.getKeyList().isEmpty()) {
            Map<String, Object> keyMap = keyHolder.getKeyList().get(0); // Get the first key map
            recipeId = (int) keyMap.get("recipeid"); // Fetch the 'recipeid' field
            return recipeId;
        } else {
            throw new IllegalStateException("Failed to retrieve the generated recipe ID.");
        }
    }

    private void insertRecipeIngredients(List<WebIngredient> webIngredients, int recipeId){
        final String SQL = "insert into ingredient (recipeid, component, quantity, measurement, preparation) values (?, ?, ?, ?, ?)";
        for(WebIngredient webIngredient : webIngredients){
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(SQL);
                ps.setInt(1, recipeId);
                ps.setString(2, webIngredient.getComponent());
                ps.setString(3, webIngredient.getQuantity());
                ps.setString(4, webIngredient.getMeasurement());
                ps.setString(5, webIngredient.getPreparation());
                return ps;
            });
        }
    }

    private void insertRecipeDirections(List<WebDirection> webDirections, int recipeId){
        int stepNum = 1;
        final String SQL = "insert into direction (recipeid, stepNum, direction, method, temp, level) values (?, ?, ?, ?, ?, ?)";
        for(WebDirection webDirection : webDirections){
            int finalStepNum = stepNum;
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(SQL);
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
    }

    private void insertNote(WebNote webNote, int recipeId) {
        if (webNote.getNote().length() > 0) {
            final String SQL = "insert into note (recipeid, note) values (?, ?)";
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(SQL);
                ps.setInt(1, recipeId);
                ps.setString(2, webNote.getNote());
                return ps;
            });
        }
    }

    private void insertLinks(List<WebLink> webLinks, int recipeId){
        if(webLinks != null){
            final String SQL = "insert into link (recipeid, link) values (?, ?)";
            for(WebLink webLink : webLinks) {
                jdbcTemplate.update(dataSource -> {
                    PreparedStatement ps = dataSource.prepareStatement(SQL);
                    ps.setInt(1, recipeId);
                    ps.setString(2, webLink.getLink());
                    return ps;
                });
            }
        }
    }

    private void insertUserSubstitutionEntries(List<WebUserSubstitutionEntry> webUserSubstitutionEntries, int recipeId){
        if(webUserSubstitutionEntries != null) {
            final String SQL = "insert into userSubs " +
                    "(recipeId, originalComponent, " +
                    "originalQuantity, originalMeasurement, " +
                    "originalPreparation, substitutedComponent, " +
                    "substitutedQuantity, " +
                    "substitutedMeasurement, substitutedPreparation) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (WebUserSubstitutionEntry webUserSubstitutionEntry : webUserSubstitutionEntries) {
                jdbcTemplate.update(dataSource -> {
                    PreparedStatement ps = dataSource.prepareStatement(SQL);
                    ps.setInt(1, recipeId);
                    ps.setString(2, webUserSubstitutionEntry.getOriginalComponent());
                    ps.setString(3, webUserSubstitutionEntry.getOriginalQuantity());
                    ps.setString(4, webUserSubstitutionEntry.getOriginalMeasurement());
                    ps.setString(5, webUserSubstitutionEntry.getOriginalPreparation());
                    ps.setString(6, webUserSubstitutionEntry.getSubstitutedComponent());
                    ps.setString(7, webUserSubstitutionEntry.getSubstitutedQuantity());
                    ps.setString(8, webUserSubstitutionEntry.getSubstitutedMeasurement());
                    ps.setString(9, webUserSubstitutionEntry.getSubstitutedPreparation());
                    return ps;
                });
            }
        }
    }

    private void insertPhoto(WebPhoto webPhoto, int recipeId){
        if(webPhoto != null){
            webPhoto.setFileName(webPhoto.getFileName().replace("|",""+recipeId));
            final String SQL = "insert into photo (recipeid, fileName) values (?, ?)";
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(SQL);
                ps.setInt(1, recipeId);
                ps.setString(2, webPhoto.getFileName());
                return ps;
            });
        }
    }

    private void insertTags(List<WebTag> webTags, int recipeId, String field){
        if(webTags != null) {
            final String SQL = "insert into " + field + " (recipeid, " + field + ") values (?, ?)";
            for (WebTag webTag : webTags) {
                jdbcTemplate.update(dataSource -> {
                    PreparedStatement ps = dataSource.prepareStatement(SQL);
                    ps.setInt(1, recipeId);
                    ps.setString(2, webTag.getField());
                    return ps;
                });
            }
        }
    }

    private void insertVariation(WebVariation webVariation, int recipeId){
        if (webVariation != null) {
            final String SQL = "insert into variation (originalrecipeid, variationrecipeid) values (?, ?)";
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(SQL);
                ps.setInt(1, webVariation.getOriginalrecipeid());
                ps.setInt(2, recipeId);
                return ps;
            });
        }
    }

    public RecipeComment addComment(int userId, int recipeId, WebComment webComment){
        int commentId;
        final String SQL = "insert into comment (recipeid, commentuserid, commenttext) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, recipeId);
            ps.setInt(2, userId);
            ps.setString(3, webComment.getCommentText());
            return ps;
        }, keyHolder);
        List<Map<String, Object>> keylist= keyHolder.getKeyList();
        if (!keyHolder.getKeyList().isEmpty()) {
            Map<String, Object> keyMap = keyHolder.getKeyList().get(0); // Get the first key map
            commentId = (int) keyMap.get("commentid"); // Fetch the 'recipeid' field
            return new RecipeComment(recipeId, commentId, userId, webComment.getCommentText(), null);
        } else {
            throw new IllegalStateException("Failed to retrieve the generated comment ID.");
        }
    }

    @Override
    public RecipeRating addRating(int userId, int recipeId, WebRating webRating){
        final String SQL = "insert into rating (recipeid, ratinguserid, ratingvalue) values (?, ?, ?)" +
                "on conflict (recipeid, ratinguserid) do update set ratingvalue = excluded.ratingvalue";
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, recipeId);
            ps.setInt(2, userId);
            ps.setInt(3, webRating.getRatingValue());
            return ps;
        });
        return new RecipeRating(recipeId, userId, webRating.getRatingValue());
    }

    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    @Override
    public Recipe getRecipeById(int recipeId){
        RecipeInfo recipeInfo = getRecipeInfoById(recipeId);
        if (recipeInfo == null) {
            return null;
        }
        List<RecipeIngredient> recipeIngredients = getRecipeIngredientsById(recipeId);
        List<RecipeDirection> recipeDirections = getRecipeDirectionsById(recipeId);

        LinkedHashMap<String, String> recipeTags = new LinkedHashMap<>();

        RecipeNote recipeNote = getNoteById(recipeId);
        List<RecipeLink> recipeLinks = getLinksById(recipeId);
        List<RecipeUserSubstitutionEntry> recipeUserSubs = getUserSubsById(recipeId);
        RecipePhoto recipePhoto = getPhotoById(recipeId);
        RecipeVariation recipeVariation = getVariationById(recipeId);
        List<RecipeTag> recipeHolidays = getTagsById(recipeId, "holiday");
        recipeTags.put("Holiday", getTagString(recipeHolidays));
        List<RecipeTag> recipeMealTypes = getTagsById(recipeId, "mealType");
        recipeTags.put("Meal Type", getTagString(recipeMealTypes));
        List<RecipeTag> recipeCuisines = getTagsById(recipeId, "cuisine");
        recipeTags.put("Cuisine", getTagString(recipeCuisines));
        List<RecipeTag> recipeAllergens = getTagsById(recipeId, "allergen");
        recipeTags.put("Allergen", getTagString(recipeAllergens));
        List<RecipeTag> recipeDietTypes = getTagsById(recipeId, "dietType");
        recipeTags.put("Diet Type", getTagString(recipeDietTypes));
        List<RecipeTag> recipeCookingLevels = getTagsById(recipeId, "cookingLevel");
        recipeTags.put("Cooking Level", getTagString(recipeCookingLevels));
        List<RecipeTag> recipeCookingStyles = getTagsById(recipeId, "cookingStyle");
        recipeTags.put("Cooking Style", getTagString(recipeCookingStyles));

        List<RecipeComment> recipeComments = getCommentsById(recipeId);

        return new Recipe(recipeInfo, recipeIngredients, recipeDirections, recipeNote, recipeLinks, recipeUserSubs, recipePhoto, recipeVariation, recipeTags, recipeComments);

    }

    //HELPER OPS
    private RecipeInfo getRecipeInfoById(int recipeId) {
        final String SQL = "select i.*, " +
                "coalesce((select avg(r.ratingvalue) from rating r WHERE r.recipeid = i.recipeid), 0) as avgRating, " +
                "coalesce((select count(r.ratingvalue) from rating r WHERE r.recipeid = i.recipeid), 0) as numRaters " +
                "from info i " +
                "where i.recipeid = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new RecipeInfoMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    private List<RecipeIngredient> getRecipeIngredientsById(int recipeId){
        final String SQL = "select * from ingredient where recipeid = ?";
        try {
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.RecipeIngredientMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<RecipeDirection> getRecipeDirectionsById(int recipeId){
        final String SQL = "select * from direction where recipeid = ?";
        try {
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.RecipeDirectionMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RecipeNote getNoteById(int recipeId){
        final String SQL = "select * from note where recipeid = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new RecipeDaoImpl.RecipeNoteMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<RecipeLink> getLinksById(int recipeId){
        final String SQL = "select * from link where recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.RecipeLinkMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<RecipeUserSubstitutionEntry> getUserSubsById(int recipeId){
        final String SQL = "select * from userSubs where recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.UserSubstitutionEntryMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RecipePhoto getPhotoById(int recipeId){
        final String SQL = "select * from photo where recipeid = ?";
        try{
            return jdbcTemplate.queryForObject(SQL, new RecipeDaoImpl.RecipePhotoMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RecipeVariation getVariationById(int recipeId){
        final String SQL = "select * from variation where variationrecipeid = ?";
        try{
            return jdbcTemplate.queryForObject(SQL, new RecipeDaoImpl.RecipeVariationMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<RecipeComment> getCommentsById(int recipeId){
        final String SQL = "select c.recipeId, c.commentId, c.commentUserId, c.commentText, concat(p.firstname, ' ' ,p.lastname) as name from comment c join profile p on c.commentUserId = p.id where c.recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.CommentMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<RecipeTag> getTagsById(int recipeId, String field){
        final String SQL = "select * from "+field+" where recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new RecipeTagMapper(field), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static String getTagString(List<RecipeTag> tags){
        StringJoiner joiner = new StringJoiner(", ");
        for (RecipeTag tag : tags) {
            joiner.add(tag.getField());
        }
        return joiner.toString();
    }

    public List<RecipeInfo> getRecipesByUserId(int userId){
        final String SQL = "select i.*, coalesce(avg(r.ratingvalue), 0) as avgRating, count(r.ratingvalue) as numRaters " +
                "from info i left join rating r on i.recipeid = r.recipeid " +
                "where i.userid = ? group BY i.userid, i.recipeid " +
                "order by i.recipeid asc";
        try{
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.RecipeInfoMapper(), userId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getUserIdByRecipeId(int recipeId){
        final String SQL = "select userid from info where recipeid = ? ";
        try{
            return jdbcTemplate.queryForObject(SQL, Integer.class, recipeId);
        }catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public WebRecipe getWebRecipeById(int recipeId){
        WebInfo recipeInfo = getWebRecipeInfoById(recipeId);
        if (recipeInfo == null) {
            return null;
        }
        List<WebIngredient> recipeIngredients = getWebRecipeIngredientsById(recipeId);
        List<WebDirection> recipeDirections = getWebRecipeDirectionsById(recipeId);


        WebNote recipeNote = getWebNoteById(recipeId);
        List<WebLink> recipeLinks = getWebLinksById(recipeId);
        List<WebUserSubstitutionEntry> recipeUserSubs = getWebUserSubsById(recipeId);

        List<WebTag> recipeHolidays = getWebTagsById(recipeId, "holiday");
        List<WebTag> recipeMealTypes = getWebTagsById(recipeId, "mealType");
        List<WebTag> recipeCuisines = getWebTagsById(recipeId, "cuisine");
        List<WebTag> recipeAllergens = getWebTagsById(recipeId, "allergen");
        List<WebTag> recipeDietTypes = getWebTagsById(recipeId, "dietType");
        List<WebTag> recipeCookingLevels = getWebTagsById(recipeId, "cookingLevel");
        List<WebTag> recipeCookingStyles = getWebTagsById(recipeId, "cookingStyle");

        return new WebRecipe(recipeInfo, recipeIngredients, recipeDirections, recipeNote, recipeLinks, recipeUserSubs, recipeHolidays, recipeMealTypes, recipeCuisines , recipeAllergens, recipeDietTypes, recipeCookingLevels, recipeCookingStyles, null);
    }

    // Helper Methods
    private WebInfo getWebRecipeInfoById(int recipeId){
        final String SQL = "select * from info where recipeid = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new RecipeDaoImpl.WebInfoMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<WebIngredient> getWebRecipeIngredientsById(int recipeId){
        final String SQL = "select * from ingredient where recipeid = ?";
        try {
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.WebIngredientMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<WebDirection> getWebRecipeDirectionsById(int recipeId){
        final String SQL = "select * from direction where recipeid = ?";
        try {
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.WebDirectionMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private WebNote getWebNoteById(int recipeId){
        final String SQL = "select * from note where recipeid = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new RecipeDaoImpl.WebNoteMapper(), recipeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<WebLink> getWebLinksById(int recipeId){
        final String SQL = "select * from link where recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.WebLinkMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<WebUserSubstitutionEntry> getWebUserSubsById(int recipeId){
        final String SQL = "select * from userSubs where recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.WebUserSubstitutionEntryMapper(), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<WebTag> getWebTagsById(int recipeId, String field){
        final String SQL = "select * from "+field+" where recipeid = ?";
        try{
            return jdbcTemplate.query(SQL, new WebTagMapper(field), recipeId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getNumberOfVariationByOriginalRecipeId(int originalRecipeId) {
        final String SQL = "select count(originalrecipeid) + 1 from variation where originalrecipeid = ?";
        try{
            return jdbcTemplate.queryForObject(SQL, Integer.class, originalRecipeId);
        }catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public int getDepthOfVariationByOriginalRecipeId(int originalRecipeId) {
        final String SQL = "with recursive variationDepth as ( "+
                "select originalrecipeid, variationrecipeid, 1 as depth " +
                "from variation " +
                "where variationrecipeid = ? " +
                "union all " +
                "select v.originalrecipeid, v.variationrecipeid, vd.depth + 1 " +
                "from variation v " +
                "join variationDepth vd on v.variationrecipeid = vd.originalrecipeid " +
                ") " +
                "select coalesce(max(depth), 0) + 1 as nextDepth " +
                "from variationDepth";
        try{
            return jdbcTemplate.queryForObject(SQL, Integer.class, originalRecipeId);
        }catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public List<RecipeInfo> getRecipesBySearchCriteria(WebSearch webSearch){
        String queryName = webSearch.getName();
        // sanitized string
        queryName = queryName.replaceAll("[^a-zA-Z0-9 ]", "");
        queryName = queryName.replaceAll("\\s+", " & ");
        String SQL = "select i.*, " +
                "coalesce((select avg(r.ratingvalue) from rating r WHERE r.recipeid = i.recipeid), 0) as avgRating, " +
                "coalesce((select count(r.ratingvalue) from rating r WHERE r.recipeid = i.recipeid), 0) as numRaters " +
                "from info i " +
                "where i.fts_document @@ to_tsquery('english', ?) ";
        // add in tag support
        SQL += "order by ts_rank(i.fts_document, to_tsquery('english', ?)) desc";
        try {
            return jdbcTemplate.query(SQL, new RecipeDaoImpl.RecipeInfoMapper(), queryName, queryName);
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
            recipeInfo.setRatingInfo(new RatingInfo(rs.getDouble("avgRating"), rs.getInt("numRaters")));
            return recipeInfo;
        }
    }

    class RecipeIngredientMapper implements RowMapper<RecipeIngredient> {
        @Override
        public RecipeIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipeId(rs.getInt("recipeId"));
            recipeIngredient.setComponent(rs.getString("component"));
            recipeIngredient.setQuantity(rs.getString("quantity"));
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

    class RecipeNoteMapper implements RowMapper<RecipeNote> {
        @Override
        public RecipeNote mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeNote recipeNote = new RecipeNote();
            recipeNote.setRecipeId(rs.getInt("recipeId"));
            recipeNote.setNote(rs.getString("note"));
            return recipeNote;
        }
    }

    class RecipeLinkMapper implements RowMapper<RecipeLink> {
        @Override
        public RecipeLink mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeLink recipeLink = new RecipeLink();
            recipeLink.setRecipeId(rs.getInt("recipeId"));
            recipeLink.setLink(rs.getString("link"));
            return recipeLink;
        }
    }

    class UserSubstitutionEntryMapper implements RowMapper<RecipeUserSubstitutionEntry> {
        @Override
        public RecipeUserSubstitutionEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeUserSubstitutionEntry recipeUserSubstitutionEntry = new RecipeUserSubstitutionEntry();
            recipeUserSubstitutionEntry.setRecipeId(rs.getInt("recipeId"));
            recipeUserSubstitutionEntry.setOriginalComponent(rs.getString("originalComponent"));
            recipeUserSubstitutionEntry.setOriginalQuantity(rs.getString("originalQuantity"));
            recipeUserSubstitutionEntry.setOriginalMeasurement(rs.getString("originalMeasurement"));
            recipeUserSubstitutionEntry.setOriginalPreparation(rs.getString("originalPreparation"));
            recipeUserSubstitutionEntry.setSubstitutedComponent(rs.getString("substitutedComponent"));
            recipeUserSubstitutionEntry.setSubstitutedQuantity(rs.getString("substitutedQuantity"));
            recipeUserSubstitutionEntry.setSubstitutedMeasurement(rs.getString("substitutedMeasurement"));
            recipeUserSubstitutionEntry.setSubstitutedPreparation(rs.getString("substitutedPreparation"));
            return recipeUserSubstitutionEntry;
        }
    }

    class RecipeVariationMapper implements RowMapper<RecipeVariation> {
        @Override
        public RecipeVariation mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeVariation recipeVariation = new RecipeVariation();
            recipeVariation.setOriginalRecipeId(rs.getInt("originalRecipeId"));
            recipeVariation.setVariationRecipeId(rs.getInt("variationRecipeId"));
            return recipeVariation;
        }
    }

    class RecipePhotoMapper implements RowMapper<RecipePhoto> {
        @Override
        public RecipePhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipePhoto recipePhoto = new RecipePhoto();
            recipePhoto.setRecipeId(rs.getInt("recipeId"));
            recipePhoto.setFileName(rs.getString("fileName"));
            recipePhoto.setFileLocation("");
            return recipePhoto;
        }
    }

    class CommentMapper implements RowMapper<RecipeComment> {
        @Override
        public RecipeComment mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecipeComment recipeComment = new RecipeComment();
            recipeComment.setRecipeId(rs.getInt("recipeId"));
            recipeComment.setCommentId(rs.getInt("commentId"));
            recipeComment.setCommentUserId(rs.getInt("commentUserId"));
            recipeComment.setCommentText(rs.getString("commentText"));
            recipeComment.setCommentUserName(rs.getString("name"));
            return recipeComment;
        }
    }

    class RatingInfoMapper implements RowMapper<RatingInfo> {
        @Override
        public RatingInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            RatingInfo ratingInfo = new RatingInfo();
            ratingInfo.setAverageRating(rs.getDouble("avgRating"));
            ratingInfo.setNumberOfRaters(rs.getInt("numRaters"));
            return ratingInfo;
        }
    }

    class WebInfoMapper implements RowMapper<WebInfo> {
        @Override
        public WebInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            WebInfo recipeInfo = new WebInfo();
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

    class WebIngredientMapper implements RowMapper<WebIngredient> {
        @Override
        public WebIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
            WebIngredient recipeIngredient = new WebIngredient();
            recipeIngredient.setComponent(rs.getString("component"));
            recipeIngredient.setQuantity(rs.getString("quantity"));
            recipeIngredient.setMeasurement(rs.getString("measurement"));
            recipeIngredient.setPreparation(rs.getString("preparation"));
            return recipeIngredient;
        }
    }

    class WebDirectionMapper implements RowMapper<WebDirection> {
        @Override
        public WebDirection mapRow(ResultSet rs, int rowNum) throws SQLException {
            WebDirection recipeDirection = new WebDirection();
            recipeDirection.setDirection(rs.getString("direction"));
            recipeDirection.setMethod(rs.getString("method"));
            recipeDirection.setTemp(rs.getInt("temp"));
            recipeDirection.setLevel(rs.getString("level"));
            return recipeDirection;
        }
    }

    class WebNoteMapper implements RowMapper<WebNote> {
        @Override
        public WebNote mapRow(ResultSet rs, int rowNum) throws SQLException {
            WebNote recipeNote = new WebNote();
            recipeNote.setNote(rs.getString("note"));
            return recipeNote;
        }
    }

    class WebLinkMapper implements RowMapper<WebLink> {
        @Override
        public WebLink mapRow(ResultSet rs, int rowNum) throws SQLException {
            WebLink recipeLink = new WebLink();
            recipeLink.setLink(rs.getString("link"));
            return recipeLink;
        }
    }

    class WebUserSubstitutionEntryMapper implements RowMapper<WebUserSubstitutionEntry> {
        @Override
        public WebUserSubstitutionEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            WebUserSubstitutionEntry recipeUserSubstitutionEntry = new WebUserSubstitutionEntry();
            recipeUserSubstitutionEntry.setOriginalComponent(rs.getString("originalComponent"));
            recipeUserSubstitutionEntry.setOriginalQuantity(rs.getString("originalQuantity"));
            recipeUserSubstitutionEntry.setOriginalMeasurement(rs.getString("originalMeasurement"));
            recipeUserSubstitutionEntry.setOriginalPreparation(rs.getString("originalPreparation"));
            recipeUserSubstitutionEntry.setSubstitutedComponent(rs.getString("substitutedComponent"));
            recipeUserSubstitutionEntry.setSubstitutedQuantity(rs.getString("substitutedQuantity"));
            recipeUserSubstitutionEntry.setSubstitutedMeasurement(rs.getString("substitutedMeasurement"));
            recipeUserSubstitutionEntry.setSubstitutedPreparation(rs.getString("substitutedPreparation"));
            return recipeUserSubstitutionEntry;
        }
    }
}