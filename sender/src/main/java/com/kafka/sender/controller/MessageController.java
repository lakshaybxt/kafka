package com.kafka.sender.controller;

import com.kafka.sender.dto.MessageDto;
import com.kafka.sender.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping(path = "/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(messageService.processMessage(messageDto));
    }

}
