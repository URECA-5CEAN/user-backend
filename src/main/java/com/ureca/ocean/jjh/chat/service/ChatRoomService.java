package com.ureca.ocean.jjh.chat.service;

import com.ureca.ocean.jjh.chat.dto.ChatMessageDto;
import com.ureca.ocean.jjh.chat.dto.ChatMessageDtoWithName;
import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface ChatRoomService {
    @Transactional
    ChatRoomResponseDto insertChatRoom(String email, UUID postId);

    List<ChatMessageDtoWithName> getChatRoomMessages(UUID chatRoomId);
}
