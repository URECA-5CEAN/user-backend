package com.ureca.ocean.jjh.user.service;

import com.ureca.ocean.jjh.common.BaseResponseDto;

import com.ureca.ocean.jjh.user.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.UserDto;
import com.ureca.ocean.jjh.user.dto.UserResultDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public UserResultDto signUp(SignUpRequestDto signUpRequestDto) ;
    public UserDto getUserByEmail(String email);

    boolean getIsDupNickname(String nickname);
}
