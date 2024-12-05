package com.example.SJSU_Event.domain.event.service;

import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.event.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Long createEvent(Long memberId, EventRequestDto dto);

    Long updateEvent(Long memberId, Long eventId ,EventRequestDto dto);

    void deleteEvent(Long memberId, Long eventId);

    Optional<Event> getById(Long eventId);

    List<Event> getEventsByOwner(Long memberId);

}
