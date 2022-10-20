package com.nanum.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor

public class KafkaRoomDto {
    private Long roomId;
    private String message;
    private LocalDate endDate;
}
