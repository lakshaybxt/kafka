package com.kafka.receiver.kafka.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageEvent {
    private String id;
    private String message;
}
