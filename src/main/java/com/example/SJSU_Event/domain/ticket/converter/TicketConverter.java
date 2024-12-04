package com.example.SJSU_Event.domain.ticket.converter;

import com.example.SJSU_Event.domain.ticket.dto.TicketResponse;
import com.example.SJSU_Event.domain.ticket.entity.Ticket;

public class TicketConverter {
    public static TicketResponse toResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketId(ticket.getId())
                .uuid(ticket.getUuid())
                .dueDate(ticket.getDueDate())
                .build();
    }
}
