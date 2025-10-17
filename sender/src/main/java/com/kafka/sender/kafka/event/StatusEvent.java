package com.kafka.sender.kafka.event;

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
