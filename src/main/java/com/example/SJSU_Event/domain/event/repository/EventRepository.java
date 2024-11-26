package com.example.SJSU_Event.domain.event.repository;

import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Member findByUsername(String username);

    void delete(Optional<Event> event);

    List<Event> findByOwner(Member owner);
}
