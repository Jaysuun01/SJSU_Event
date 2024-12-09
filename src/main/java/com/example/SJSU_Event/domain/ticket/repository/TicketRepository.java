package com.example.SJSU_Event.domain.ticket.repository;

import com.example.SJSU_Event.domain.ticket.entity.Ticket;
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

@Repository
public class TicketRepository {

    private final JdbcTemplate jdbcTemplate;

    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * Save a new ticket
     * @param ticket Ticket entity to save
     * @return Saved ticket with generated ID and UUID
     */
    public Ticket save(Ticket ticket) {
        String sql = """
        INSERT INTO ticket (uuid, due_date, event_id, member_id) 
        VALUES (?, ?, ?, ?)
    """;

        ticket.generateData();  // insert 전에 UUID 생성 보장

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ticket_id"});
            ps.setString(1, ticket.getUuid());
            ps.setDate(2, Date.valueOf(ticket.getDueDate()));
            ps.setLong(3, ticket.getEventId());
            ps.setLong(4, ticket.getMemberId());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        ticket.setId(key);
        return ticket;
    }
    /**
     * Find ticket by UUID
     * @param uuid UUID of the ticket
     * @return Optional containing the ticket if found, empty Optional otherwise
     */
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
    /**
     * Find ticket by event ID
     * @param eventId ID of the event
     * @return Optional containing the ticket if found, empty Optional otherwise
     */
    public Optional<Ticket> findByEventId(Long eventId) {
        String sql = """
        SELECT 
            ticket_id as id,
            created_date as createdDate,
            last_modified_date as lastModifiedDate,
            due_date as dueDate,
            event_id as eventId,
            uuid,
            member_id as memberId
        FROM ticket 
        WHERE event_id = ?
    """;

        try {
            Ticket ticket = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            Ticket.builder()
                                    .id(rs.getLong("id"))
                                    .uuid(rs.getString("uuid"))
                                    .dueDate(rs.getDate("dueDate") != null ?
                                            rs.getDate("dueDate").toLocalDate() : null)
                                    .eventId(rs.getLong("eventId"))
                                    .memberId(rs.getLong("memberId"))
                                    .build()
                    , eventId);
            return Optional.ofNullable(ticket);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    /**
     * Delete ticket by ID and member ID
     * @param ticketId ID of the ticket to delete
     * @param memberId ID of the member who owns the ticket
     * @throws RuntimeException if ticket not found
     */
    public void deleteByTicketId(Long ticketId, Long memberId) {
        String sql = "DELETE FROM ticket WHERE ticket_id = ? AND member_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, ticketId, memberId);

        if (rowsAffected == 0) {
            throw new RuntimeException("No tickets found with ticketId: " + ticketId);
        }
    }
}

