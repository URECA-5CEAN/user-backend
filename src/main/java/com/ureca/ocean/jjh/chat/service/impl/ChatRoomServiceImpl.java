package com.ureca.ocean.jjh.chat.service.impl;

import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import com.ureca.ocean.jjh.chat.repository.ChatRoomRepository;
import com.ureca.ocean.jjh.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;


    public ChatRoomResponseDto insertChatRoom(){

        //POST 글 의 주인의 ID , 로그인한 사용자의 ID
        //POST의 ID


        return new ChatRoomResponseDto();
    }

}
