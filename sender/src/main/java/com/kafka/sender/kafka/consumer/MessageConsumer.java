package com.kafka.sender.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.sender.kafka.event.MessageEvent;
import com.kafka.sender.kafka.event.StatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.kafka.sender.kafka.topics.KafkaTopics.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = SUCCESS_TOPIC, groupId = "sender-group")
    public void consumeSuccessEvent(String message) {
        try {
            log.info("Received Status of Message from topic {}: {}", MESSAGE_TOPIC, message);
            StatusEvent event = objectMapper.readValue(message, StatusEvent.class);
            log.info("Status Event: {}", event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = FAILURE_TOPIC, groupId = "sender-group")
    public void consumeFailureEvent(String message) {
        try {
            log.info("Received Failure Status of Message from topic {}: {}", MESSAGE_TOPIC, message);
            StatusEvent event = objectMapper.readValue(message, StatusEvent.class);
            log.info("Status Event Failure: {}", event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
