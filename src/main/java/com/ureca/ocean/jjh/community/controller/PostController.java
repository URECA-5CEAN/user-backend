package com.ureca.ocean.jjh.community.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostRequestDto;
import com.ureca.ocean.jjh.community.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/article")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postServiceImpl;
    @PostMapping
    public ResponseEntity<BaseResponseDto<?>> insertPost(@Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
                                                         @RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postServiceImpl.insertPost(encodedEmail,postRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(postResponseDto));
    }

}
