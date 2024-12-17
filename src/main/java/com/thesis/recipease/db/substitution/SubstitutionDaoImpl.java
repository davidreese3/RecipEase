package com.thesis.recipease.db.substitution;

import com.thesis.recipease.model.GlossaryEntry;
import com.thesis.recipease.model.SubstitutionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubstitutionDaoImpl implements SubstitutionDao{
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
    public List<SubstitutionEntry> getAllSubstitutionEntries(){
        final String SQL = "select * from knownSubs order by originalComponent asc";
        List<SubstitutionEntry> substitutions = jdbcTemplate.query(SQL, new SubstitutionDaoImpl.SubstitutionEntryMapper());
        return substitutions;
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
    class SubstitutionEntryMapper implements RowMapper<SubstitutionEntry> {
        @Override
        public SubstitutionEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            SubstitutionEntry substitutionEntry = new SubstitutionEntry();
            substitutionEntry.setOriginalComponent(rs.getString("originalComponent"));
            substitutionEntry.setOriginalQuantity(rs.getInt("originalQuantity"));
            substitutionEntry.setOriginalMeasurement(rs.getString("originalMeasurement"));
            substitutionEntry.setOriginalPreparation(rs.getString("originalPreparation"));
            substitutionEntry.setSubstitutedComponent(rs.getString("substitutedComponent"));
            substitutionEntry.setSubstitutedQuantity(rs.getInt("substitutedQuantity"));
            substitutionEntry.setSubstitutedMeasurement(rs.getString("substitutedMeasurement"));
            substitutionEntry.setSubstitutedPreparation(rs.getString("substitutedPreparation"));
            return substitutionEntry;
        }
    }
}
