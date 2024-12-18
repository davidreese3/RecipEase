package com.thesis.recipease.db.profile;

import com.thesis.recipease.model.Account;
import com.thesis.recipease.model.Profile;
import com.thesis.recipease.model.WebAccount;
import com.thesis.recipease.model.WebProfile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public interface ProfileDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    // READ OPS
    public Profile getProfileByEmail(String email);
    // UPDATE OPS
    public Profile updateProfile(WebProfile webProfile);
    // DELETE OPS


}



