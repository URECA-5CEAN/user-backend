package com.ureca.ocean.jjh.community.service.impl;


import com.ureca.ocean.jjh.community.dto.response.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostRequestDto;
import com.ureca.ocean.jjh.community.entity.Post;
import com.ureca.ocean.jjh.community.repository.PostRepository;
import com.ureca.ocean.jjh.community.service.PostService;
import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Override
    public List<PostResponseDto> listPost(int pageNo, String criteria, String location){

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, criteria));

        if(location.isEmpty()){
            return postRepository.findAll(pageable).map(PostResponseDto::of).getContent(); //of : entity->dto
        }

        return postRepository.findByLocation(pageable,location).map(PostResponseDto::of).getContent(); //of : entity->dto
    }
}
