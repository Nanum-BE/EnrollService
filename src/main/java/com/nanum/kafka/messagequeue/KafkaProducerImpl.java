package com.nanum.kafka.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanum.kafka.dto.KafkaRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, KafkaRoomDto kafkaRoomDto) {
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(kafkaRoomDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, s);
    }
}
