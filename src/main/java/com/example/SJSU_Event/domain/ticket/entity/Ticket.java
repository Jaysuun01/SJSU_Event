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
import java.util.UUID;

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

    private Long eventId;

    private Long memberId;


    public static Ticket of(Long eventId, Long memberId,String uuid, LocalDate localDate) {
        return Ticket.builder()
                .eventId(eventId)
                .memberId(memberId)
                .uuid(uuid)
                .dueDate(localDate)
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    public void generateData() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
