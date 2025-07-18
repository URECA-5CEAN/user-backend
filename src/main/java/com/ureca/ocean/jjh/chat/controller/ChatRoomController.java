package com.ureca.ocean.jjh.chat.controller;

import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import com.ureca.ocean.jjh.chat.service.impl.ChatRoomServiceImpl;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomServiceImpl chatRoomService;
    @PostMapping
    public ResponseEntity<BaseResponseDto<?>> insertChatRoom(){
        ChatRoomResponseDto ChatRoomResponseDto = chatRoomService.insertChatRoom();
        return ResponseEntity.ok(BaseResponseDto.success(ChatRoomResponseDto));
    }
}
