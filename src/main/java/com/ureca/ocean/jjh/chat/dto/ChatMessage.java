package com.ureca.ocean.jjh.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String roomId;   // 채팅방 id
    private String sender;   // 보낸 사람
    private String content;  // 메시지 내용
}
