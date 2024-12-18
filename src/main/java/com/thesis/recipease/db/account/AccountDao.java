package com.thesis.recipease.db.account;


import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.WebAccount;
import com.thesis.recipease.model.WebProfile;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.util.List;

@Repository
public interface AccountDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Account addAccount(WebAccount webAccount, List<String> roles, WebProfile webProfile);
    // READ OPS
    public Account getAccountByEmail(String email);
    public Integer getActivationCodeByEmail(String email);
    public String getPasswordByEmail(String email);
    public String updatePasswordByEmail(String email, String password);
    // UPDATE OPS
    public boolean verifyActivationCodeAndActivate(String email, int code);
    public String updateEmailByEmail(String originalEmail, String newEmail);
    // DELETE OPS
    public String deleteAccountByEmail(String email);

}
