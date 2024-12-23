package com.toyhe.app.Tickets.Repository;

import com.toyhe.app.Tickets.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
