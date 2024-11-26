package com.example.SJSU_Event.domain.ticket.service;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.ticket.entity.Ticket;
import com.example.SJSU_Event.domain.ticket.exception.TicketHandler;
import com.example.SJSU_Event.domain.ticket.repository.TicketRepository;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    @Override
    public Ticket findTicketByEventId(Long eventId, Member member) {
        Ticket ticket = ticketRepository.findByEventId(eventId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));
        validateHost(ticket, member);
        return ticket;
    }
    private void validateHost(Ticket ticket, Member host) {
        if (!ticket.getEvent().getEventOwner().equals(host)) {
            throw new TicketHandler(ErrorStatus.TICKET_ONLY_CAN_BE_OPENED_BY_OWNER);
        }
    }
}
