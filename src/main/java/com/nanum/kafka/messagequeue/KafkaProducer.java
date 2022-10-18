package com.nanum.kafka.messagequeue;

import com.nanum.kafka.dto.KafkaRoomDto;

public interface KafkaProducer {
    void send(String topic, KafkaRoomDto kafkaRoomDto);
}
