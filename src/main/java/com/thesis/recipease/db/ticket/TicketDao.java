package com.thesis.recipease.db.ticket;

import com.thesis.recipease.model.domain.ticket.Ticket;
import com.thesis.recipease.model.web.ticket.WebTicket;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public interface TicketDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Ticket addTicket(WebTicket webTicket);
    // READ OPS
    // UPDATE OPS
    // DELETE OPS
}
