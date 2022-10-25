package com.nanum.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class KafkaUserDto {
    private Long userId;
    private String message;
}
