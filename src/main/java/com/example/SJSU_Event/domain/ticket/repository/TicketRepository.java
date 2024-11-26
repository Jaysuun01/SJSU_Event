package com.example.SJSU_Event.domain.ticket.repository;

import com.example.SJSU_Event.domain.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
