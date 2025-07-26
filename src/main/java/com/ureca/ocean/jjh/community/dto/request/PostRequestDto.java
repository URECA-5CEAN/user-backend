package com.ureca.ocean.jjh.community.dto.request;

import com.ureca.ocean.jjh.community.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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

    private String category;
    private UUID brandId;
    private UUID benefitId;

    private LocalDateTime promiseDate;

    private String location;
    public static PostRequestDto from(Post post) {
        return PostRequestDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .brandId(post.getBrandId())
                .benefitId(post.getBenefitId())
                .build();
    }

}
