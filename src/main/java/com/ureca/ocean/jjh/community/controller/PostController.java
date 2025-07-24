package com.ureca.ocean.jjh.community.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostListResponseDto;
import com.ureca.ocean.jjh.community.dto.response.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostRequestDto;
import com.ureca.ocean.jjh.community.service.impl.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/article")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostServiceImpl postServiceImpl;

    @Operation(summary = "게시글 작성", description = "사용자가 게시글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 작성 성공"),
    })
    @PostMapping
    public ResponseEntity<BaseResponseDto<?>> insertPost(
            @Parameter(description = "Base64 인코딩된 사용자 이메일", hidden = true) @RequestHeader("X-User-email") String encodedEmail,
            @RequestBody PostRequestDto postRequestDto) {

        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        PostResponseDto postResponseDto = postServiceImpl.insertPost(email, postRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(postResponseDto));
    }

    @Operation(summary = "게시글 위치 목록 조회", description = "게시글에 등록된 지역(위치) 목록들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "위치 목록 조회 성공"),
    })
    @GetMapping("/locations")
    public ResponseEntity<BaseResponseDto<?>> listLocations() {
        return ResponseEntity.ok(BaseResponseDto.success(postServiceImpl.listLocations()));
    }

    @Operation(summary = "게시글 목록 조회", description = "전체 게시글 목록을 페이지네이션과 정렬 기준, 위치 필터로 조회합니다. paramter 아무것도 없으면 전체 조회, 필요한것만 넣으시면 됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
    })
    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> listPost(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,

            @Parameter(description = "정렬 기준 (createdAt, likeCount 등)", example = "createdAt")
            @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria,

            @Parameter(description = "지역 필터", example = "initial-address")
            @RequestParam(required = false, defaultValue = "", value = "location") String location) {

        PostListResponseDto postListResponseDto = PostListResponseDto.of(postServiceImpl.listPost(pageNo, criteria, location));
        return ResponseEntity.ok(BaseResponseDto.success(postListResponseDto));
    }

    @GetMapping("/detail")
    public ResponseEntity<BaseResponseDto<?>> detailPost(@RequestParam UUID postId) {
        PostResponseDto postResponseDto = postServiceImpl.detailPost(postId);
        return ResponseEntity.ok(BaseResponseDto.success(postResponseDto));
    }
}
