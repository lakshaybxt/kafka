package com.kafka.receiver.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.receiver.kafka.event.MessageEvent;
import com.kafka.receiver.kafka.event.StatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.kafka.receiver.kafka.topics.KafkaTopics.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, StatusEvent> kafkaTemplate;

    @KafkaListener(topics = MESSAGE_TOPIC, groupId = "receiver-group-one")
    public void consumeMessage(String message) {
        String eventId = "UNKNOWN";
        try {
            log.info("Received Message from topic {}: {}", MESSAGE_TOPIC, message);
            MessageEvent event = objectMapper.readValue(message, MessageEvent.class);
            log.info("Message Event: {}", event);
            eventId = event.getId();
            StatusEvent statusEvent = StatusEvent.builder()
                    .messageId(eventId)
                    .status("RECEIVED")
                    .build();
            kafkaTemplate.send(SUCCESS_TOPIC, statusEvent);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            StatusEvent statusEvent = StatusEvent.builder()
                    .messageId(eventId)
                    .status("FAILED")
                    .build();
            kafkaTemplate.send(FAILURE_TOPIC, statusEvent);
        }
    }
}
