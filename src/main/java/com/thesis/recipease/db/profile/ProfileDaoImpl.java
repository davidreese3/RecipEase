package com.thesis.recipease.db.profile;

import com.thesis.recipease.db.account.AccountDaoImpl;
import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.Profile;
import com.thesis.recipease.model.WebProfile;
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
    public Profile getProfileByEmail(String email) {
        final String SQL = "select * from profile where email = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new ProfileDaoImpl.ProfileMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    // ------------------------------------------------
    // UPDATE OPS
    // ------------------------------------------------

    @Override
    public WebProfile updateProfile(WebProfile webProfile) {
        final String SQL = "update profile set firstName = ?, lastName = ?, cookingLevel = ?, favoriteDish = ? , favoriteCuisine = ? where email = ?";
        jdbcTemplate.update(SQL, webProfile.getFirstName(), webProfile.getLastName(), webProfile.getCookingLevel(), webProfile.getFavoriteDish(), webProfile.getFavoriteCuisine(), webProfile.getEmail());
        return webProfile;
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
            profile.setEmail(rs.getString("email"));
            profile.setFirstName(rs.getString("firstName"));
            profile.setLastName(rs.getString("lastName"));
            profile.setCookingLevel(rs.getString("cookingLevel"));
            profile.setFavoriteDish(rs.getString("favoriteDish"));
            profile.setFavoriteCuisine(rs.getString("favoriteCuisine"));
            return profile;
        }
    }
}
