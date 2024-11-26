package com.example.SJSU_Event.domain.event.service;

import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.event.repository.EventRepository;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.EventHandler;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;

    @Override
    public Long createEvent(String username, EventRequestDto dto) {
        Member owner = memberRepository.findByUsername(username);
        Event saveEvent = eventRepository.save(Event.of(owner, dto));
        return saveEvent.getId();
    }

    @Override
    public Long updateEvent(String username, Long eventId ,EventRequestDto dto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EventHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));

        validateWriter(member, event);

        event.update(dto);
        return eventRepository.save(event).getId();
    }

    @Override
    public void deleteEvent(String username, Long eventId) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EventHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));

        validateWriter(member, event);
        eventRepository.delete(event);
    }

    @Override
    public Optional<Event> getById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public List<Event> getEventsByOwner(String username) {
        Member owner = memberRepository.findByUsername(username);
        return eventRepository.findByOwner(owner);
    }
    private static void validateWriter(Member member, Event event) {
        if (!member.equals(event.getEventOwner())) {
            throw new EventHandler(ErrorStatus.PROMOTION_ONLY_CAN_BE_TOUCHED_BY_WRITER);
        }
}
