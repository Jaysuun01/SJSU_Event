package com.example.SJSU_Event.domain.ticket.repository;

import com.example.SJSU_Event.domain.ticket.entity.Ticket;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {

    private final JdbcTemplate jdbcTemplate;

    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Ticket> findByUuid(String uuid) {
        String sql = """
        SELECT 
            ticket_id as id,
            uuid,
            due_date as dueDate,
            event_id as eventId
        FROM ticket 
        WHERE uuid = ?
    """;

        try {
            Ticket ticket = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            Ticket.builder()
                                    .id(rs.getLong("ticket_id"))
                                    .uuid(rs.getString("uuid"))
                                    .dueDate(rs.getDate("due_date") != null ?
                                            rs.getDate("due_date").toLocalDate() : null)
                                    .eventId(rs.getLong("event_id"))
                                    .build()
                    , uuid);
            return Optional.ofNullable(ticket);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Ticket> findByEventId(Long eventId) {
        String sql = """
        SELECT 
            ticket_id as id,
            uuid,
            due_date as dueDate,
            event_id as eventId
        FROM ticket 
        WHERE event_id = ?
    """;

        try {
            Ticket ticket = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            Ticket.builder()
                                    .id(rs.getLong("ticket_id"))
                                    .uuid(rs.getString("uuid"))
                                    .dueDate(rs.getDate("due_date") != null ?
                                            rs.getDate("due_date").toLocalDate() : null)
                                    .eventId(rs.getLong("event_id"))
                                    .build()
                    , eventId);
            return Optional.ofNullable(ticket);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteByEventId(Long eventId) {
        String sql = "DELETE FROM ticket WHERE event_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, eventId);

        if (rowsAffected == 0) {
            throw new RuntimeException("No tickets found with eventId: " + eventId);
        }
    }
}

