package com.ureca.ocean.jjh.community.service;

import com.ureca.ocean.jjh.community.dto.response.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostRequestDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponseDto insertPost(String email, PostRequestDto postRequestDto);

    List<PostResponseDto> listPost(int pageNo, String criteria, String location);

    PostResponseDto detailPost(UUID postId);

    List<String> listLocations();
}
