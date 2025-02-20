package com.thesis.recipease.db.account;

import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.profile.WebProfile;
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
import com.thesis.recipease.util.generator.VerificationCodeGenerator;


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
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // insert into account
            final String accountSQL = "insert into account (email, password, active, verificationcode) values (?, ?, false, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(dataSource -> {
                PreparedStatement ps = dataSource.prepareStatement(accountSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, webAccount.getEmail());
                ps.setString(2, webAccount.getPassword());
                ps.setInt(3, webAccount.getVerificationCode());
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
    public Integer getVerificationCodeById(int id) {
        final String SQL = "select verificationCode from account where id = ?";
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
    public boolean verifyVerificationCodeAndActivate(int id, int code, int verificationCode) {
        if(verificationCode == code){
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

    @Override
    public Account deactivateAccountById(int id) {
        final String SQL = "update account set active = false where id = ?";
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL);
            ps.setInt(1, id);
            return ps;
        });
        return getAccountById(id);
    }

    public int generateAndSaveVerificationCode(int id){
        int verificationCode = VerificationCodeGenerator.generateVerificationCode();
        final String SQL = "update account set verificationCode = ? where id = ?";
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL);
            ps.setInt(1, verificationCode);
            ps.setInt(2,id);
            return ps;
        });
        return verificationCode;
    }

    // ------------------------------------------------
    // DELETE OPS
    // ------------------------------------------------

    @Override
    public int deleteAccountById(int id) {
        final String SQL = "delete from account where id = ?";
        try {
            jdbcTemplate.update(SQL, id);
        } catch (DataAccessException e) {
            return -1;
        }
        return 0;
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
            account.setVerificationCode(rs.getInt("verificationCode"));
            return account;
        }
    }
}
