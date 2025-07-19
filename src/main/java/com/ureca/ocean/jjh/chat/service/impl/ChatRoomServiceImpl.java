package com.ureca.ocean.jjh.chat.service.impl;

import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import com.ureca.ocean.jjh.chat.repository.ChatRoomRepository;
import com.ureca.ocean.jjh.chat.service.ChatRoomService;
import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    @Transactional
    public ChatRoomResponseDto insertChatRoom(String email, UUID postId){

        //POST 글 의 주인의 ID , 로그인한 사용자의 ID
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));


        //POST의 ID



        return new ChatRoomResponseDto();
    }

}
