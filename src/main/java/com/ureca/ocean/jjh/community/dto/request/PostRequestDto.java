package com.ureca.ocean.jjh.community.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class PostRequestDto {
    private String title;
    private String content;
}
