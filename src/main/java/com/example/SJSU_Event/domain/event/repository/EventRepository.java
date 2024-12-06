package com.example.SJSU_Event.domain.event.repository;

import com.example.SJSU_Event.domain.event.entity.Event;
import com.example.SJSU_Event.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public void delete(Event event) {
        String sql = "DELETE FROM event WHERE event_id = ?";
        jdbcTemplate.update(sql, event.getId());
    }

    public List<Event> findByOwner(Long ownerId) {
        String sql = "SELECT * FROM event WHERE event_owner_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        Event.builder()
                                .id(rs.getLong("event_id"))
                                .eventOwnerId(rs.getLong("event_owner_id"))
                                .title(rs.getString("title"))
                                .maxAudience(rs.getInt("max_audience"))
                                .entranceFee(rs.getInt("entrance_fee"))
                                .showDate(rs.getDate("show_date") != null ? rs.getDate("show_date").toLocalDate() : null)
                                .startTime(rs.getString("start_time"))
                                .endTime(rs.getString("end_time"))
                                .build()
                , ownerId);
    }

    public Event save(Event event) {
        log.info("eventOwnerId = {}", event.getEventOwnerId());
        String sql = "INSERT INTO event (event_owner_id, title, max_audience, entrance_fee, show_date, start_time, end_time) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new
                    String[]{"id"});
                ps.setLong(1, event.getEventOwnerId());
                ps.setString(2, event.getTitle());  // Add this
                ps.setInt(3, event.getMaxAudience());
                ps.setInt(4, event.getEntranceFee());
                ps.setDate(5, Date.valueOf(event.getShowDate()));
                ps.setString(6, event.getStartTime());
                ps.setString(7, event.getEndTime());
            return ps;
        },keyHolder);
        long key = keyHolder.getKey().longValue();
        event.setId(key);
        return event;
    }

    public void update(Event event) {
        String sql = "UPDATE event SET title = ?, max_audience = ?, entrance_fee = ?, show_date = ?, start_time = ?, end_time = ? " +
                     "WHERE event_id = ?";
        jdbcTemplate.update(sql,
                event.getTitle(),
                event.getMaxAudience(),
                event.getEntranceFee(),
                event.getShowDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getId());
    }

    public Optional<Event> findById(Long eventId) {
        String sql = "SELECT * FROM event WHERE event_id = ?";

        try {
            Event event = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            Event.builder()
                                    .id(rs.getLong("event_id"))
                                    .eventOwnerId(rs.getLong("event_owner_id"))
                                    .title(rs.getString("title"))
                                    .maxAudience(rs.getInt("max_audience"))
                                    .entranceFee(rs.getInt("entrance_fee"))
                                    .showDate(rs.getDate("show_date") != null ? rs.getDate("show_date").toLocalDate() : null)
                                    .startTime(rs.getString("start_time"))
                                    .endTime(rs.getString("end_time"))
                                    .build()
                    , eventId);
            return Optional.ofNullable(event);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}


