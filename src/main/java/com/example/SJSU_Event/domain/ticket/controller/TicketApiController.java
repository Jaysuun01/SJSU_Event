package com.example.SJSU_Event.domain.ticket.controller;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.service.MemberService;
import com.example.SJSU_Event.domain.ticket.converter.TicketConverter;
import com.example.SJSU_Event.domain.ticket.dto.TicketResponse;
import com.example.SJSU_Event.domain.ticket.entity.Ticket;
import com.example.SJSU_Event.domain.ticket.service.TicketService;
import com.example.SJSU_Event.global.exception.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@RestController
public class TicketApiController {
    private final TicketService ticketService;
    private final MemberService memberService;
    @GetMapping("/event/{eventId}/member/{memberId}")
    public ApiResponseDto<TicketResponse> findTicketByEventId(@PathVariable Long eventId, @PathVariable Long memberId) {
        Ticket ticket = ticketService.findTicketByEventId(eventId, memberId);
        return ApiResponseDto.onSuccess(TicketConverter.toResponse(ticket));
    }
}