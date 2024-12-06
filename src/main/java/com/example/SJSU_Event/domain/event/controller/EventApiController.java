package com.example.SJSU_Event.domain.event.controller;

import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.event.service.EventService;
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

@Tag(name = "Event API", description = "Event API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/events")
@RequiredArgsConstructor
@RestController
public class EventApiController {
    private final EventService eventService;

    @Operation(summary = "Create Event 🔑", description = "Login user can create the event")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
    })
    @PostMapping("/{memberId}")
    public ApiResponseDto<Long> createEvent(
            @PathVariable Long memberId,
            @RequestBody EventRequestDto eventRequest) {
        return ApiResponseDto.onSuccess(eventService.createEvent(memberId, eventRequest));
    }

    @Operation(summary = "Modify Event 🔑", description = "The user can modify the event")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @PutMapping("/{eventId}/member/{memberId}")
    public ApiResponseDto<Long> updateEvent(
            @PathVariable Long memberId,
            @PathVariable Long eventId,
            @RequestBody EventRequestDto eventRequest) {
        return ApiResponseDto.onSuccess(eventService.updateEvent(memberId, eventId, eventRequest));
    }

    @Operation(summary = "Delete Event 🔑", description = "The user can delete the event")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @DeleteMapping("/{eventId}/member/{memberId}")
    public ApiResponseDto<Void> deleteEvent(
            @PathVariable Long memberId,
            @PathVariable Long eventId) {
        eventService.deleteEvent(memberId, eventId);
        return ApiResponseDto.onSuccess(null);
    }

    @Operation(summary = "Search Event", description = "Search the only one event ")
    @ApiErrorCodeExample({
            ErrorStatus.EVENT_NOT_FOUND
    })
    @GetMapping("/{eventId}")
    public ApiResponseDto<Event> getById(@PathVariable Long eventId) {
        return ApiResponseDto.onSuccess(eventService.getById(eventId).orElseThrow(() ->
                new RuntimeException("Event not found with id: " + eventId)));
    }

    @Operation(summary = "Search Event list", description = "Search the all events that user created")
    @ApiErrorCodeExample(value = {
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @GetMapping("/member/{memberId}")
    public ApiResponseDto<List<Event>> getEventsByOwner(@PathVariable Long memberId) {
        return ApiResponseDto.onSuccess(eventService.getEventsByOwner(memberId));
    }
}