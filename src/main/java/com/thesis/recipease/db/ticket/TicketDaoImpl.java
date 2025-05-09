package com.thesis.recipease.db.ticket;

import com.thesis.recipease.model.domain.ticket.Ticket;
import com.thesis.recipease.model.web.ticket.TicketCriteria;
import com.thesis.recipease.model.web.ticket.WebTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TicketDaoImpl implements TicketDao{
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
    @Override
    public Ticket addTicket(WebTicket webTicket){
        int id;
        final String SQL = "insert into ticket (email, classifier, subject, note, open) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, webTicket.getEmail());
            ps.setString(2, webTicket.getClassifier());
            ps.setString(3, webTicket.getSubject());
            ps.setString(4, webTicket.getNote());
            ps.setBoolean(5, true);
            return ps;
        }, keyHolder);
        List<Map<String, Object>> keylist= keyHolder.getKeyList();
        if (!keyHolder.getKeyList().isEmpty()) {
            Map<String, Object> keyMap = keyHolder.getKeyList().get(0); // Get the first key map
            id = (int) keyMap.get("id");
            return new Ticket(id, webTicket.getEmail(), webTicket.getClassifier(), webTicket.getSubject(), webTicket.getNote(), false);
        } else {
            throw new IllegalStateException("Failed to retrieve the generated ID.");
        }
    }

    // ------------------------------------------------
    // READ OPS
    // ------------------------------------------------
    public List<Ticket> getAllTickets(){
        String SQL = "select * from ticket order by id asc";
        List<Ticket> tickets = jdbcTemplate.query(SQL, new TicketDaoImpl.TicketEntryMapper());
        return tickets;
    }
    //Used later with search
    /*
    public List<Ticket> getTicketsByCriteria(TicketCriteria ticketCriteria){
        String SQL = "select * from ticket where 1=1 ";
        List<Object> params = new ArrayList<>();
        if(ticketCriteria.getClassifier() != null){
            SQL += "and classifier = ? ";
            params.add(ticketCriteria.getClassifier());
        }
        if(ticketCriteria.getSolved() != null){
            SQL += "and solved = ? ";
            params.add(ticketCriteria.getSolved());
        }
        SQL += "order by id asc";
        List<Ticket> tickets = jdbcTemplate.query(SQL, new TicketDaoImpl.TicketEntryMapper(), params.toArray());
        return tickets;
    }*/

    public Ticket getTicketById(int id){
        String SQL = "select * from ticket where id = ?";
        try {
            Ticket ticket = jdbcTemplate.queryForObject(SQL, new TicketDaoImpl.TicketEntryMapper(), id);
            return ticket;
        } catch (DataAccessException e) {
            return null;
        }
    }

    // ------------------------------------------------
    // UPDATE OPS
    // ------------------------------------------------
    public int updateTicketOpenStatus(int id, boolean open){
        final String SQL = "update ticket set open = ? where id = ?";
        try {
            jdbcTemplate.update(SQL, open, id);
        } catch (DataAccessException e) {
            return -1;
        }
        return 0;
    }

    // ------------------------------------------------
    // DELETE OPS
    // ------------------------------------------------

    // ------------------------------------------------
    // MAPPERS
    // ------------------------------------------------
    class TicketEntryMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getInt("id"));
            ticket.setEmail(rs.getString("email"));
            ticket.setClassifier(rs.getString("classifier"));
            ticket.setSubject(rs.getString("subject"));
            ticket.setNote(rs.getString("note"));
            ticket.setOpen(rs.getBoolean("open"));
            return ticket;
        }
    }
}
