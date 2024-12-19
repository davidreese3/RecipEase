package com.thesis.recipease.db.account;

import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.web.WebAccount;
import com.thesis.recipease.model.web.WebProfile;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        int id;
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

            //get id

            List<Map<String, Object>> keylist= keyHolder.getKeyList();
            if (!keyHolder.getKeyList().isEmpty()) {
                Map<String, Object> keyMap = keyHolder.getKeyList().get(0); // Get the first key map
                id = (int) keyMap.get("id"); // Fetch the 'id' field
            } else {
                throw new IllegalStateException("Failed to retrieve the generated user ID.");
            }
            // insert into authority
            final String rolesSQL = "insert into authority (email, role) values (?, ?)";
            for (String role : roles) {
                jdbcTemplate.update(rolesSQL, webAccount.getEmail(), role);
            }

            // insert into profile
            final String profileSQL = "insert into profile (id, firstname, lastname, cookinglevel, favoritedish, favoritecuisine) values (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(profileSQL);
                ps.setInt(1, id);
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

        return getAccountById(id);
    }

    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    @Override
    public Account getAccountById(int id) {
        final String SQL = "select * from account where id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new AccountMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Account getAccountByEmail(String email) {
        final String SQL = "select * from account where email = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new AccountMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getActivationCodeById(int id) {
        final String SQL = "select activationCode from account where id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, Integer.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getPasswordById(int id){
        final String SQL = "select password from account where id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // ------------------------------------------------
    // UPDATE OPS
    // ------------------------------------------------

    @Override
    public boolean verifyActivationCodeAndActivate(int id, int code, int activationCode) {
        if(activationCode == code){
            final String SQL = "update account set active = true where id = ?";
            jdbcTemplate.update(SQL, id);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Account updateEmailById(int id, String newEmail){
        final String SQL = "update account set email = ? where id = ?";
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL);
            ps.setString(1, newEmail);
            ps.setInt(2, id);
            return ps;
        });
        return getAccountByEmail(newEmail);
    }

    @Override
    public Account updatePasswordById(int id, String password){
        final String SQL = "update account set password = ? where id = ?";
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL);
            ps.setString(1, password);
            ps.setInt(2, id);
            return ps;
        });
        return getAccountById(id);
    }
    // ------------------------------------------------
    // DELETE OPS
    // ------------------------------------------------

    @Override
    public Account deleteAccountById(int id) {
        final String getSQL = "select * from account where id = ?";
        Account account = getAccountById(id);
        final String SQL = "delete from account where id = ?";
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL);
            ps.setInt(1, id);
            return ps;
        });
        return account;
    }

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
