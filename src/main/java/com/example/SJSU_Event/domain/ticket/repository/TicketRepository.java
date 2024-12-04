package com.example.SJSU_Event.domain.ticket.repository;

import com.example.SJSU_Event.domain.ticket.entity.Ticket;
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

    // findByUuid: UUID를 기준으로 티켓 조회
    public Optional<Ticket> findByUuid(String uuid) {
        String sql = "SELECT * FROM ticket WHERE uuid = ?";
        List<Ticket> tickets = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class), uuid);
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0));
    }

    // findByEventId: eventId를 기준으로 티켓 조회
    public Optional<Ticket> findByEventId(Long eventId) {
        String sql = "SELECT * FROM ticket WHERE event_id = ?";
        List<Ticket> tickets = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class), eventId);
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0));
    }

    // deleteByEventId: eventId를 기준으로 티켓 삭제
    public void deleteByEventId(Long eventId) {
        String sql = "DELETE FROM ticket WHERE event_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, eventId);

        if (rowsAffected == 0) {
            throw new RuntimeException("No tickets found with eventId: " + eventId);
        }
    }
}

