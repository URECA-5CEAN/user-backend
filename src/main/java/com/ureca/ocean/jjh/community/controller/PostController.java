package com.ureca.ocean.jjh.community.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostListResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostRequestDto;
import com.ureca.ocean.jjh.community.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/user/article")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostServiceImpl postServiceImpl;
    @PostMapping
    public ResponseEntity<BaseResponseDto<?>> insertPost(@Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
                                                         @RequestBody PostRequestDto postRequestDto){
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        PostResponseDto postResponseDto = postServiceImpl.insertPost(email,postRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(postResponseDto));
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> listPost( @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                        @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria,
                                                        @RequestParam(required = false, defaultValue = "", value = "location") String location){
        PostListResponseDto postListResponseDto = PostListResponseDto.of(postServiceImpl.listPost(pageNo, criteria,location));
        return ResponseEntity.ok(BaseResponseDto.success(postListResponseDto));
    }

}
