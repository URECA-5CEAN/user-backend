package com.ureca.ocean.jjh.user.dto.response;

import lombok.Builder;

import java.util.UUID;
@Builder
public class UserStatusResponseDto {
    private UUID userId;
    private Long level;
    private Long exp;
}
