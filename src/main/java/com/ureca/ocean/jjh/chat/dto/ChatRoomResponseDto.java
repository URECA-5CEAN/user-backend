package com.ureca.ocean.jjh.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter @Setter
public class ChatRoomResponseDto {

    @Schema(description = "채팅방 ID")
    private UUID chatRoomId;

    @Schema(description = "내 ID")
    private UUID me;

    @Schema(description = "상대방 사용자 ID")
    private UUID other;
}
