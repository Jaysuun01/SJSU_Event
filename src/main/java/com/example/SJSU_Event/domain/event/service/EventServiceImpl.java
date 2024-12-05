package com.example.SJSU_Event.domain.event.service;

import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.event.exception.EventHandler;
import com.example.SJSU_Event.domain.event.repository.EventRepository;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.exception.MemberHandler;
import com.example.SJSU_Event.domain.member.repository.MemberRepository;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;

    @Override
    public Long createEvent(Long memberId, EventRequestDto dto) {
        Member owner = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        log.info("owner Id: {}" , owner.getId());
        Event saveEvent = eventRepository.save(Event.of(owner.getId(), dto));
        return saveEvent.getId();
    }

    @Override
    public Long updateEvent(Long memberId, Long eventId, EventRequestDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));

        validateWriter(member, event);

        // Event 클래스에 update 메소드 구현
        event.update(dto);  // dto의 내용을 event에 반영
        eventRepository.update(event);
        return eventId;
    }

    @Override
    public void deleteEvent(Long memberId, Long eventId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));

        validateWriter(member, event);
        eventRepository.delete(event);  // Optional.of(event) 대신 event 직접 전달
    }

    @Override
    public Optional<Event> getById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public List<Event> getEventsByOwner(Long memberId) {
        return eventRepository.findByOwner(memberId);
    }

    private static void validateWriter(Member member, Event event) {
        if (!member.getId().equals(event.getEventOwnerId())) {  // member의 id와 event의 owner id를 비교
            throw new EventHandler(ErrorStatus.EVENT_ONLY_TOUCHED_BY_OWNER);
        }
    }
}
