package com.ureca.ocean.jjh.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Schema(description = "게시글 작성 요청 DTO")
public class PostRequestDto {

    @Schema(description = "게시글 제목", example = "집앞에 gs25 가실 분!")
    private String title;

    @Schema(description = "게시글 내용", example = "ㅈㄱㄴ")
    private String content;
}
