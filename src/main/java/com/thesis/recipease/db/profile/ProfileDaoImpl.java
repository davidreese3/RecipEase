package com.thesis.recipease.db.profile;

import com.thesis.recipease.model.Profile;
import com.thesis.recipease.model.web.WebProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProfileDaoImpl implements ProfileDao{
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

    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    @Override
    public Profile getProfileById(int id) {
        final String SQL = "select * from profile where id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new ProfileDaoImpl.ProfileMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    // ------------------------------------------------
    // UPDATE OPS
    // ------------------------------------------------

    @Override
    public Profile updateProfile(WebProfile webProfile) {
        final String SQL = "update profile set firstName = ?, lastName = ?, cookingLevel = ?, favoriteDish = ? , favoriteCuisine = ? where id = ?";
        jdbcTemplate.update(SQL, webProfile.getFirstName(), webProfile.getLastName(), webProfile.getCookingLevel(), webProfile.getFavoriteDish(), webProfile.getFavoriteCuisine(), webProfile.getId());
        return getProfileById(webProfile.getId());
    }

    // ------------------------------------------------
    // DELETE OPS
    // ------------------------------------------------

    // ------------------------------------------------
    // MAPPERS
    // ------------------------------------------------
    class ProfileMapper implements RowMapper<Profile> {
        @Override
        public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
            Profile profile = new Profile();
            profile.setId(rs.getInt("id"));
            profile.setFirstName(rs.getString("firstName"));
            profile.setLastName(rs.getString("lastName"));
            profile.setCookingLevel(rs.getString("cookingLevel"));
            profile.setFavoriteDish(rs.getString("favoriteDish"));
            profile.setFavoriteCuisine(rs.getString("favoriteCuisine"));
            return profile;
        }
    }
}
