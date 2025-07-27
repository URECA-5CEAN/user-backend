package com.ureca.ocean.jjh.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ChatRoomRequestDto {

    @Schema(description="약속이 만들어질 post ",example = "ccc0e0ca-ce19-4d99-9f98-e283e7f4102e")
    private UUID postId;


}
