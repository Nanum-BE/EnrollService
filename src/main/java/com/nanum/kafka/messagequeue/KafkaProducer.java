package com.nanum.kafka.messagequeue;

import com.nanum.kafka.dto.KafkaRoomDto;
import com.nanum.kafka.dto.KafkaUserDto;

public interface KafkaProducer {
    void send(String topic, KafkaRoomDto kafkaRoomDto);

    void sendUserIdToSMS(String topic, KafkaUserDto kafkaUserDto);
}
