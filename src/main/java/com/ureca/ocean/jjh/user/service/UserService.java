package com.ureca.ocean.jjh.user.service;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.user.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public UserDto getUserByEmail(String email);
}
