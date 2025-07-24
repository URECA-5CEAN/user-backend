package com.ureca.ocean.jjh.community.dto.response;

import com.ureca.ocean.jjh.community.entity.Post;
import com.ureca.ocean.jjh.user.dto.response.UserResponseDto;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostResponseDto {
    private UUID postId;
    private String title;
    private String content;
    private UserResponseDto author;

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(UserResponseDto.of(post.getAuthor()))
                .build();
    }
}
