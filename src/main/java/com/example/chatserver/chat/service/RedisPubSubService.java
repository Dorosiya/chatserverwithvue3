package com.example.chatserver.chat.service;

import com.example.chatserver.chat.dto.ChatMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisPubSubService implements MessageListener {

    private final StringRedisTemplate stringRedisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    public RedisPubSubService(@Qualifier("chatPubSub") StringRedisTemplate stringRedisTemplate, SimpMessageSendingOperations messagingTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.messagingTemplate = messagingTemplate;
    }



    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

    // pattern에는 topic의 이름의 패턴이 담겨있고, 이 퍁넝르 기반으로 다이나믹한 코딩이 가능하다.
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String payload = new String(message.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDto chatMessageDto = null;
        try {
            chatMessageDto = objectMapper.readValue(payload, ChatMessageDto.class);
            messagingTemplate.convertAndSend("/topic/" + chatMessageDto.getRoomId(), chatMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
