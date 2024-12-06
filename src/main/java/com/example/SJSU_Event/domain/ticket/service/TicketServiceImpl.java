package com.example.SJSU_Event.domain.ticket.service;

import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.event.exception.EventHandler;
import com.example.SJSU_Event.domain.event.repository.EventRepository;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.exception.MemberHandler;
import com.example.SJSU_Event.domain.member.repository.MemberRepository;
import com.example.SJSU_Event.domain.ticket.entity.Ticket;
import com.example.SJSU_Event.domain.ticket.exception.TicketHandler;
import com.example.SJSU_Event.domain.ticket.repository.TicketRepository;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    @Override
    public Ticket findTicketByEventId(Long eventId, Long memberId) {

        Ticket ticket = ticketRepository.findByEventId(eventId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));

        if (!event.getEventOwnerId().equals(memberId)) {
            throw new TicketHandler(ErrorStatus.TICKET_ONLY_CAN_BE_OPENED_BY_OWNER);
        }
        return ticket;
    }

    @Override
    public Ticket createTicket(Long eventId, Long memberId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if(!event.getEventOwnerId().equals(memberId)) {
            throw new EventHandler(ErrorStatus.EVENT_ONLY_TOUCHED_BY_OWNER);
        }

        Ticket ticket = Ticket.of(eventId, memberId, null, event.getShowDate());
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId, Long memberId) {
        ticketRepository.deleteByTicketId(ticketId, memberId);
    }

}