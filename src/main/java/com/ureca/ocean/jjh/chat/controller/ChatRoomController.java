package com.ureca.ocean.jjh.chat.controller;

import com.ureca.ocean.jjh.chat.dto.*;
import com.ureca.ocean.jjh.chat.service.impl.ChatRoomServiceImpl;
import com.ureca.ocean.jjh.common.BaseResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/chatRoom")
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {
    private final ChatRoomServiceImpl chatRoomService;
    @PostMapping
    public ResponseEntity<BaseResponseDto<?>> insertChatRoom(@Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
                                                             @RequestBody ChatRoomRequestDto chatRoomRequestDto){
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        ChatRoomResponseDto ChatRoomResponseDto = chatRoomService.insertChatRoom(email, chatRoomRequestDto.getPostId());
        return ResponseEntity.ok(BaseResponseDto.success(ChatRoomResponseDto));
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> getChatRoomMessages(@RequestParam UUID chatRoomId){
        List<ChatRoomMessageResponseDto> ChatMessageDto = chatRoomService.getChatRoomMessages(chatRoomId);
        return ResponseEntity.ok(BaseResponseDto.success(ChatMessageDto));
    }


}
