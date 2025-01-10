package com.thesis.recipease.db.profile;

import com.thesis.recipease.model.domain.profile.Profile;
import com.thesis.recipease.model.web.profile.WebProfile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public interface ProfileDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    // READ OPS
    public Profile getProfileById(int id);
    public String getNameById(int id);
    // UPDATE OPS
    public Profile updateProfile(WebProfile webProfile);
    // DELETE OPS
}



