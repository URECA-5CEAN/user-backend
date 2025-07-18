package com.ureca.ocean.jjh.user.service;

import com.ureca.ocean.jjh.user.dto.request.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.response.UserResponseDto;
import jakarta.transaction.Transactional;

public interface UserService {
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) ;
    public UserResponseDto getUserByEmail(String email);

    boolean getIsDupNickname(String nickname);

    UserResponseDto getCurrentUserInfo(String email);

    @Transactional
    UserResponseDto updateUserInfo(String email, String nickname, String address, String password);
}
