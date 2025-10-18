package com.kafka.receiver.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.receiver.kafka.event.MessageEvent;
import com.kafka.receiver.kafka.event.StatusEvent;
import com.kafka.receiver.kafka.event.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ListenerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, StatusEvent> kafkaTemplate;

    @KafkaListener(topics = "topic_all_receive_test", groupId = "receiver-group-one")
    public void listen(String message, Acknowledgment ack)  {
        System.out.println("MS1 received: " + message);
        try {
            TestEvent event = objectMapper.readValue(message, TestEvent.class);
            log.info("Message Event: {}", event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ack.acknowledge();
    }
}
