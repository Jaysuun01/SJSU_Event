package com.example.SJSU_Event.domain.event.service;

import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.event.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Long createEvent(String username, EventRequestDto dto);

    Long updateEvent(String username, Long eventId ,EventRequestDto dto);

    void deleteEvent(String username, Long eventId);

    Optional<Event> getById(Long eventId);

    List<Event> getEventsByOwner(String username);

}
