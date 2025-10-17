package com.kafka.sender.service;

import com.kafka.sender.dto.MessageDto;
import com.kafka.sender.kafka.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.kafka.sender.kafka.topics.KafkaTopics.MESSAGE_TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    public String processMessage(MessageDto message) {
        MessageEvent messageEvent = MessageEvent.builder()
                .id(String.valueOf(message.getId()))
                .message(message.getMessage())
                .build();

        try {
            log.info("Sending Message to Kafka: {}", messageEvent);
            kafkaTemplate.send(MESSAGE_TOPIC, messageEvent);
            return "Message sent to Kafka successfully";
        } catch (Exception e) {
            return "Failed to send message to Kafka: " + e.getMessage();
        }
    }
}
