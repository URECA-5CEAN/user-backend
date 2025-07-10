package com.ureca.ocean.jjh.signup.service;

import com.ureca.ocean.jjh.signup.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.UserDto;
import com.ureca.ocean.jjh.user.dto.UserResultDto;

public interface SignUpService {
    public UserResultDto signUp(SignUpRequestDto signUpRequestDto) ;
}
