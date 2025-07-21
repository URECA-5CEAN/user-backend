package com.ureca.ocean.jjh.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter @Setter
public class ChatRoomResponseDto {
    private UUID ChatRoomId;
    private UUID user1Id;
    private UUID user2Id;

}
