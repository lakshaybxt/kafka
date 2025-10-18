package com.kafka.sender.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
    private String id;
    private String message;
}
