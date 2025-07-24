package com.ureca.ocean.jjh.community.service;

import com.ureca.ocean.jjh.community.dto.response.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostRequestDto;

import java.util.List;

public interface PostService {
    PostResponseDto insertPost(String email, PostRequestDto postRequestDto);

    List<PostResponseDto> listPost(int pageNo, String criteria, String location);

    List<String> listLocations();
}
