package com.example.SJSU_Event.domain.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EventRequestDto {
    @Schema(required = true, description = "Event title")
    private String title;
    private int maxAudience;
    @Schema(required = true, description = "Event Fee")
    private int entranceFee;
    private LocalDate showDate;
    private String startTime;
    private String endTime;
}
