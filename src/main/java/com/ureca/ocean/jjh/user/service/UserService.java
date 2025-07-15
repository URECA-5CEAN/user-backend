package com.ureca.ocean.jjh.user.service;

import com.ureca.ocean.jjh.user.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.UserResponseDto;

public interface UserService {
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) ;
    public UserResponseDto getUserByEmail(String email);

    boolean getIsDupNickname(String nickname);

    UserResponseDto getCurrentUserInfo(String email);
}
