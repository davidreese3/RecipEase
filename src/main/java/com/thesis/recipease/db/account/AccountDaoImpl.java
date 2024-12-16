package com.thesis.recipease.db.account;

import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.WebAccount;
import com.thesis.recipease.model.WebProfile;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao{
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
    public Account addAccount(WebAccount webAccount, List<String> roles, WebProfile webProfile) {
        Random random = new Random();
        int activationCode = 100000 + random.nextInt(900000);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // insert into account
            final String accountSQL = "insert into account (email, password, active, activationcode) values (?, ?, false, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(accountSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, webAccount.getEmail());
                ps.setString(2, webAccount.getPassword());
                ps.setInt(3, activationCode);
                return ps;
            }, keyHolder);

            // insert into authority
            final String rolesSQL = "insert into authority (email, role) values (?, ?)";
            for (String role : roles) {
                jdbcTemplate.update(rolesSQL, webAccount.getEmail(), role);
            }

            // insert into profile
            final String profileSQL = "insert into profile (email, firstname, lastname, cookinglevel, favoritedish, favoritecuisine) values (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(profileSQL);
                ps.setString(1, webProfile.getEmail());
                ps.setString(2, webProfile.getFirstName());
                ps.setString(3, webProfile.getLastName());
                ps.setString(4, webProfile.getCookingLevel());
                ps.setString(5, webProfile.getFavoriteDish());
                ps.setString(6, webProfile.getFavoriteCuisine());
                return ps;
            });
            transactionManager.commit(status);
        }
        catch (DataAccessException e) {
            System.out.println("Error in adding user: " + webAccount.getEmail());
            transactionManager.rollback(status);
            throw e;
        }

        return getAccountByEmail(webAccount.getEmail());
    }

    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    @Override
    public Account getAccountByEmail(String email) {
        final String SQL = "select * from account where email = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new AccountMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer getActivationCodeByEmail(String email) {
        final String SQL = "select activationCode from account where email = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public boolean verifyActivationCodeAndActivate(String email, int code) {
        if(getActivationCodeByEmail(email) == code){
            final String SQL = "update account set active = true where email = ?";
            jdbcTemplate.update(SQL, email);
            return true;
        }
        else {
            return false;
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
    class AccountMapper implements RowMapper<Account>{
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException{
            Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setEmail(rs.getString("email"));
            account.setPassword(rs.getString("password"));
            account.setActivationCode(rs.getInt("activationCode"));
            return account;
        }
    }
}
