package com.ureca.ocean.jjh.signup.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.signup.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.signup.service.SignUpService;
import com.ureca.ocean.jjh.user.dto.UserDto;
import com.ureca.ocean.jjh.user.dto.UserResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class SignUpController {
    private final SignUpService signUpService;
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto<?>>  signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        log.info("signup controller 내의 request : " + signUpRequestDto.getAddress());
        UserResultDto userResultDto = signUpService.signUp(signUpRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(userResultDto));
    }
}
