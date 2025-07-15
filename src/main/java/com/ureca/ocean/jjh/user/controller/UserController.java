package com.ureca.ocean.jjh.user.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.exception.ErrorCode;

import com.ureca.ocean.jjh.user.dto.request.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.response.UserResponseDto;
import com.ureca.ocean.jjh.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userServiceImpl;
    @PostMapping("/health")
    public String health(){
        log.info("user health checking...");
        return "user health check fine";
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> getUserByEmail(@RequestParam String email){
        UserResponseDto userDto = userServiceImpl.getUserByEmail(email);
        //email 기준으로 해당 사용자가 없을 경우
        if(userDto.getId() == null){
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.NOT_FOUND_USER));
        }
        log.info(userDto.getPassword());
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    @GetMapping("/isDupNickname")
    public ResponseEntity<BaseResponseDto<?>> getIsDupNickname(@RequestParam String nickname){
        return ResponseEntity.ok(BaseResponseDto.success(userServiceImpl.getIsDupNickname(nickname)));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto<?>>  signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto userDto = userServiceImpl.signUp(signUpRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    @PostMapping("/currentUserInfo")
    public ResponseEntity<BaseResponseDto<?>>  getCurrentUserInfo(@RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend내의 currnet userEmail : {}", email);
        log.info("user-backend내의 currnet userEmail : " + email);
        UserResponseDto userDto = userServiceImpl.getCurrentUserInfo(email);
        if(userDto.getId() == null){
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.NOT_FOUND_USER));
        }
        log.info(userDto.getPassword());
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

}
