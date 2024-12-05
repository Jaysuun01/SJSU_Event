package com.example.SJSU_Event.domain.ticket.controller;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.service.MemberService;
import com.example.SJSU_Event.domain.ticket.converter.TicketConverter;
import com.example.SJSU_Event.domain.ticket.dto.TicketResponse;
import com.example.SJSU_Event.domain.ticket.entity.Ticket;
import com.example.SJSU_Event.domain.ticket.service.TicketService;
import com.example.SJSU_Event.global.annotation.api.ApiErrorCodeExample;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import com.example.SJSU_Event.global.exception.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ticket API", description = "Ticket API")
@ApiResponse(responseCode = "2000", description = "Success")
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@RestController
public class TicketApiController {
    private final TicketService ticketService;
    private final MemberService memberService;

    @Operation(summary = "Find Ticket 🔑", description = "Find Ticket with eventId")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
    })
    @GetMapping("/event/{eventId}/member/{memberId}")
    public ApiResponseDto<TicketResponse> findTicketByEventId(@PathVariable Long eventId, @PathVariable Long memberId) {
        Ticket ticket = ticketService.findTicketByEventId(eventId, memberId);
        return ApiResponseDto.onSuccess(TicketConverter.toResponse(ticket));
    }
}