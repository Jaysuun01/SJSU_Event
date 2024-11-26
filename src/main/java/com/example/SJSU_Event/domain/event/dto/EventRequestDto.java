package com.example.SJSU_Event.domain.event.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EventRequestDto {
    private int maxAudience;
    private int entranceFee;
    private LocalDate showDate;
    private String startTime;
    private String endTime;
}
