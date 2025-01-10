package com.thesis.recipease.db.substitution;

import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface SubstitutionDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    // READ OPS
    public List<SubstitutionEntry> getAllSubstitutionEntries();
    // UPDATE OPS
    // DELETE OPS
}
