package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.service.ChatService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


}
