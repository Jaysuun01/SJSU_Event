package com.example.SJSU_Event.domain.ticket.entity;

import com.example.SJSU_Event.domain.auditing.entity.BaseTimeEntity;
import com.example.SJSU_Event.domain.event.entity.Event;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "ticket")
public class Ticket extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Ticket of(Event event, String uuid, LocalDate localDate) {
        return Ticket.builder()
                .event(event)
                .uuid(uuid)
                .dueDate(localDate)
                .build();
    }
}
