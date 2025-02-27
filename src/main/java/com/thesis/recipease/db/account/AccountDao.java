package com.thesis.recipease.db.account;


import com.thesis.recipease.model.domain.account.Account;
import com.thesis.recipease.model.user.User;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.profile.WebProfile;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.util.List;

@Repository
public interface AccountDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Account addAccount(WebAccount webAccount, List<String> roles, WebProfile webProfile);
    // READ OPS
    public Account getAccountById(int id);
    public Account getAccountByEmail(String email);
    public Integer getVerificationCodeById(int id);
    public String getPasswordById(int id);
    public List<User> getUsers();
    // UPDATE OPS
    public Account updatePasswordById(int id, String password);
    public boolean verifyVerificationCodeAndActivate(int id, int code, int verificationCode);
    public Account updateEmailById(int id, String newEmail);
    public int deactivateAccountById(int id);
    public int generateAndSaveVerificationCode(int id);
    public int reactivateAccountById(int id);
    // DELETE OPS
    public int deleteAccountById(int id);

}
