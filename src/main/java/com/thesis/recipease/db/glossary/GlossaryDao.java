package com.thesis.recipease.db.glossary;

import com.thesis.recipease.model.GlossaryEntry;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface GlossaryDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    // READ OPS
    public List<GlossaryEntry> getAllGlossaryEntries();
    // UPDATE OPS
    // DELETE OPS
}
