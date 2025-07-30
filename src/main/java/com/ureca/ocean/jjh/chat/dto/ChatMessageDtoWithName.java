package com.ureca.ocean.jjh.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDtoWithName {
    private String message;
    private UUID chatRoomId;
    private String userName;
    private LocalDateTime time;
}
