package com.thesis.recipease.db.ticket;

import com.thesis.recipease.model.domain.ticket.Ticket;
import com.thesis.recipease.model.web.ticket.TicketCriteria;
import com.thesis.recipease.model.web.ticket.WebTicket;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface TicketDao {
    public void setDataSource(DataSource dataSource);
    // CREATE OPS
    public Ticket addTicket(WebTicket webTicket);
    // READ OPS
    public List<Ticket> getAllTickets();
    public Ticket getTicketById(int id);
    //public List<Ticket> getTicketsByCriteria(TicketCriteria ticketCriteria);
    // UPDATE OPS
    public int updateTicketOpenStatus(int id, boolean open);
    // DELETE OPS
}
