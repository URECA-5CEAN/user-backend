package com.ureca.ocean.jjh.chat.controller;


import com.ureca.ocean.jjh.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    // 클라이언트가 "/app/chat.sendMessage" 로 메시지 보내면 처리
    @MessageMapping("/chat.sendMessage.{roomId}")
    @SendTo("/topic/chat.room.{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage message) {
        // roomId로 메시지 구분, 해당 방에만 브로드캐스트
        return message;
    }


}
