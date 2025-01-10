package com.thesis.recipease.db.glossary;

import com.thesis.recipease.model.domain.glossary.GlossaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GlossaryDaoImpl implements  GlossaryDao{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

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
    public List<GlossaryEntry> getAllGlossaryEntries(){
        final String SQL = "select * from glossary order by term asc";
        List<GlossaryEntry> glossary = jdbcTemplate.query(SQL, new GlossaryEntryMapper());
        return glossary;
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
    class GlossaryEntryMapper implements RowMapper<GlossaryEntry> {
        @Override
        public GlossaryEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            GlossaryEntry glossaryEntry = new GlossaryEntry();
            glossaryEntry.setTerm(rs.getString("term"));
            glossaryEntry.setDefinition(rs.getString("definition"));
            glossaryEntry.setRelatedTerms(rs.getString("relatedForms").split(", "));
            return glossaryEntry;
        }
    }
}
