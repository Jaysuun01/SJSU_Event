package com.example.SJSU_Event.domain.event.repository;

import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.info("eventOwnerId = {}", event.getEventOwnerId());
        String sql = "INSERT INTO event (event_owner_id, max_audience, entrance_fee, show_date, start_time, end_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new
                    String[]{"id"});
                ps.setLong(1, event.getEventOwnerId());
                ps.setInt(2,event.getMaxAudience());
                ps.setInt(3,event.getEntranceFee());
                ps.setDate(4, Date.valueOf(event.getShowDate()));
                ps.setString(5,event.getStartTime());
            ps.setString(6,event.getEndTime());
            return ps;
        },keyHolder);
        long key = keyHolder.getKey().longValue();
        event.setId(key);
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


