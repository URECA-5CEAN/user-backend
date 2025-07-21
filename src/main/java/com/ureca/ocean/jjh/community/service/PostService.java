package com.ureca.ocean.jjh.community.service;

import com.ureca.ocean.jjh.community.dto.request.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostRequestDto;

public interface PostService {
    PostResponseDto insertPost(String email, PostRequestDto postRequestDto);
}
