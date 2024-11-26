package com.example.SJSU_Event.domain.event.controller;


import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.apache.naming.ResourceRef.AUTH;

@Tag(name = "Event API", description = "이벤트 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/events")
@RequiredArgsConstructor
@RestController
public class EventApiController {
    private final EventCommandService eventCommandService;
    private final EventQueryService eventQueryService;

    @Operation(summary = "이벤트 작성 🔑", description = "로그인한 회원이 이벤트를 작성합니다.")
    @ApiErrorCodeExample(status = AUTH)
    @PostMapping
    public ApiResponseDto<Long> createEvent(
            @AuthUser Member member,
            @RequestBody @Validated EventRequestDto eventRequest) {
        return ApiResponseDto.onSuccess(eventCommandService.createEvent(member, eventRequest));
    }

    @Operation(summary = "이벤트 수정 🔑", description = "로그인한 회원이 작성한 이벤트를 수정합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
            ErrorStatus.NO_PERMISSION,
            ErrorStatus.MEMBER_NOT_FOUND
    }, status = AUTH)
    @PutMapping("/{eventId}")
    public ApiResponseDto<Long> updateEvent(
            @AuthUser Member member,
            @PathVariable Long eventId,
            @RequestBody @Validated EventRequestDto eventRequest) {
        return ApiResponseDto.onSuccess(eventCommandService.updateEvent(member, eventId, eventRequest));
    }

    @Operation(summary = "이벤트 삭제 🔑", description = "로그인한 회원이 작성한 이벤트를 삭제합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
            ErrorStatus.NO_PERMISSION,
            ErrorStatus.MEMBER_NOT_FOUND
    }, status = AUTH)
    @DeleteMapping("/{eventId}")
    public ApiResponseDto<Boolean> deleteEvent(
            @AuthUser Member member,
            @PathVariable Long eventId) {
        eventCommandService.deleteEvent(member, eventId);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "이벤트 단건 조회", description = "이벤트 ID로 단건 조회합니다.")
    @ApiErrorCodeExample({
            ErrorStatus.EVENT_NOT_FOUND
    })
    @GetMapping("/{eventId}")
    public ApiResponseDto<Event> getEvent(@PathVariable Long eventId) {
        return ApiResponseDto.onSuccess(eventQueryService.getEventById(eventId));
    }

    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록을 페이징하여 조회합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @GetMapping("/my")
    public ApiResponseDto<List<Event>> getMyEvents(
            @AuthUser Member member,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponseDto.onSuccess(eventQueryService.getEventsByOwner(member, pageable));
    }

    @Operation(summary = "전체 이벤트 목록 조회", description = "전체 이벤트 목록을 페이징하여 조회합니다.")
    @GetMapping
    public ApiResponseDto<List<Event>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponseDto.onSuccess(eventQueryService.getAllEvents(pageable));
    }
}
