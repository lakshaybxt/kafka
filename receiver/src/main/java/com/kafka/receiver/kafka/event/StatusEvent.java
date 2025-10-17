package com.kafka.receiver.kafka.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusEvent {
    private String messageId;
    private String status;
}
