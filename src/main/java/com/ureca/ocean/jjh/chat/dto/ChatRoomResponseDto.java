package com.ureca.ocean.jjh.chat.dto;

import com.ureca.ocean.jjh.community.entity.Post;
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

    private Post post;

    @Schema(description = "내 ID")
    private UUID me;

    @Schema(description = "상대방 사용자 ID")
    private UUID other;
}
