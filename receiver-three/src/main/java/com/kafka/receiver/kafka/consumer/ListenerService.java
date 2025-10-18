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

    @KafkaListener(topics = "topic_every_receive_test", groupId = "receiver-group-three")
    public void listen(String message, Acknowledgment ack)  {
        System.out.println("MS3 received: " + message);
        try {
            TestEvent event = objectMapper.readValue(message, TestEvent.class);
            log.info("Test Event: {}", event);
            new Thread(() -> {
                try {
                    Thread.sleep(5 * 60 * 1000); // 5 minute delay
                    log.info("Test Event id: {}", event.getId());
                    ack.acknowledge(); // ✅ Ack after 1 minute
                    log.info("MS3 acknowledged message after 5 minute: {}", event);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Ack delay interrupted", e);
                }
            }).start();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ack.acknowledge();
    }
}
