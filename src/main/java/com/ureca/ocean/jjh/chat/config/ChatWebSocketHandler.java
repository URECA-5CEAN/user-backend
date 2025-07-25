package com.ureca.ocean.jjh.chat.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 채팅방 ID별로 연결된 세션을 관리
    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결 초기에는 아무 채팅방에도 소속되지 않음
        String ip = session.getRemoteAddress().getAddress().getHostAddress();
        log.info("새 연결: IP = " + ip + ", Session ID = " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> payload = mapper.readValue(message.getPayload(), new TypeReference<>() {});

        String type = payload.get("type");
        String roomId = payload.get("roomId");
        System.out.println("type :" + type );
        if ("join".equals(type)) {
            // 채팅방에 세션만 등록하고 메시지는 보내지 않음
            chatRooms.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
            System.out.println(">> " + roomId + "에 " + session.getId() + " 입장");
        } else if ("chat".equals(type)) {
            // 메시지를 해당 채팅방의 모든 세션에 브로드캐스트
            System.out.println("broad cast");
            chatRooms.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session); // 혹시 모르니 추가 ( join 메시지를 보내지 않고, 바로 채팅메시지만 보낼 경우 )

            for (WebSocketSession s : chatRooms.get(roomId)) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(message.getPayload()));
                }
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 제거
        System.out.println("afterConnectionClosed ");
        chatRooms.values().forEach(set -> set.remove(session));
    }
}
