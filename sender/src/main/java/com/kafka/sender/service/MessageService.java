package com.kafka.sender.service;

import com.kafka.sender.dto.MessageDto;
import com.kafka.sender.dto.TestDto;
import com.kafka.sender.kafka.event.MessageEvent;
import com.kafka.sender.kafka.event.TestEvent;
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
    private final KafkaTemplate<String, TestEvent> kafkaTestTemplate;

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

    public String processTest(TestDto testDto) {
        TestEvent testEvent = TestEvent.builder()
                .id(testDto.getId())
                .message(testDto.getMessage())
                .build();

        try {
            log.info("Sending Test Event to Kafka: {}", testEvent);
            log.info("Test Event id: {}", testEvent.getId());
            kafkaTestTemplate.send("topic_every_receive_test", testEvent);
            return "Test Event sent to Kafka successfully";
        } catch (Exception e) {
            return "Failed to send Test Event to Kafka: " + e.getMessage();
        }
    }
}
