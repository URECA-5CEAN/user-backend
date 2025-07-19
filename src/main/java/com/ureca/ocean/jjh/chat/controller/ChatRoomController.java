package com.ureca.ocean.jjh.chat.controller;

import com.ureca.ocean.jjh.chat.dto.ChatRoomRequestDto;
import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import com.ureca.ocean.jjh.chat.service.impl.ChatRoomServiceImpl;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomServiceImpl chatRoomService;
    @PostMapping
    public ResponseEntity<BaseResponseDto<?>> insertChatRoom(@Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
                                                             @RequestBody ChatRoomRequestDto chatRoomRequestDto){
        ChatRoomResponseDto ChatRoomResponseDto = chatRoomService.insertChatRoom(encodedEmail, chatRoomRequestDto.getPostId());
        return ResponseEntity.ok(BaseResponseDto.success(ChatRoomResponseDto));
    }
}
