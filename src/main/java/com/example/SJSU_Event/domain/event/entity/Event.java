package com.example.SJSU_Event.domain.event.entity;

import com.example.SJSU_Event.domain.auditing.entity.BaseTimeEntity;
import com.example.SJSU_Event.domain.event.dto.EventRequestDto;
import com.example.SJSU_Event.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "event")
public class Event extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private Long eventOwnerId;

    private int maxAudience;
    private int entranceFee;
    private LocalDate showDate;
    private String startTime;
    private String endTime;

    public static Event of(Long owner, EventRequestDto dto) {
        return Event.builder()
                .eventOwnerId(owner)
                .maxAudience(dto.getMaxAudience())
                .entranceFee(dto.getEntranceFee())
                .showDate(dto.getShowDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    public void update(EventRequestDto dto) {
    }

    public void setId(long id) {
        this.id = id;
    }

}
