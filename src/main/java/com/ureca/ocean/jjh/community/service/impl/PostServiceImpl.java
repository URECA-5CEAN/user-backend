package com.ureca.ocean.jjh.community.service.impl;


import com.ureca.ocean.jjh.community.dto.request.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostRequestDto;
import com.ureca.ocean.jjh.community.entity.Post;
import com.ureca.ocean.jjh.community.repository.PostRepository;
import com.ureca.ocean.jjh.community.service.PostService;
import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public PostResponseDto insertPost(String email, PostRequestDto postRequestDto){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));

        Post post = Post.builder()
                        .title(postRequestDto.getTitle())
                        .content(postRequestDto.getContent())
                        .author(user)
                        .build();

        Post newPost = postRepository.save(post);
        return PostResponseDto.of(newPost);
    }
}
