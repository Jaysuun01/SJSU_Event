package com.example.SJSU_Event.domain.ticket.service;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.ticket.entity.Ticket;

import java.util.Optional;

public interface TicketService {
    Ticket findTicketByEventId(Long eventId, Long memberId);
    Ticket createTicket(Long eventId, Long memberId);
    void deleteTicket(Long ticketId, Long memberId);
}
