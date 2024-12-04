package com.example.SJSU_Event.domain.event.repository;

import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.member.entity.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void delete(Optional<Event> event) {
        if (event.isPresent()) {
            String sql = "DELETE FROM event WHERE event_id = ?";
            jdbcTemplate.update(sql, event.get().getId());
        }
    }

    public List<Event> findByOwner(Long ownerId) {
        String sql = "SELECT * FROM event WHERE event_owner_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Event.class), ownerId);
    }

    // 새로운 메서드들 추가
    public Event save(Event event) {
        String sql = "INSERT INTO event (event_owner_id, max_audience, entrance_fee, show_date, start_time, end_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                event.getEventOwnerId(),
                event.getMaxAudience(),
                event.getEntranceFee(),
                event.getShowDate(),
                event.getStartTime(),
                event.getEndTime());
        return event;
    }

    public void update(Event event) {
        String sql = "UPDATE event SET max_audience = ?, entrance_fee = ?, show_date = ?, start_time = ?, end_time = ? " +
                "WHERE event_id = ?";
        jdbcTemplate.update(sql,
                event.getMaxAudience(),
                event.getEntranceFee(),
                event.getShowDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getId());
    }

    public Optional<Event> findById(Long eventId) {
        String sql = "SELECT * FROM event WHERE event_id = ?";
        List<Event> events = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Event.class), eventId);
        return events.isEmpty() ? Optional.empty() : Optional.of(events.get(0));
    }
}


