package com.ureca.ocean.jjh.user.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.dto.response.UserStatusResponseDto;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.entity.UserStatus;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import com.ureca.ocean.jjh.user.repository.UserStatusRepository;
import com.ureca.ocean.jjh.user.service.UserStatusService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserStatusServiceImpl implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatusResponseDto getUserStatus(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));

        UserStatus userStatus = userStatusRepository.findById(user.getId()).orElseThrow(()->new UserException(ErrorCode.USER_STATUS_NOT_EXIST));

        return UserStatusResponseDto.builder()
                .userId(userStatus.getId())
                .exp(userStatus.getExp())
                .level(userStatus.getLevel())
                .build();

    }

    @Override
    public UserStatusResponseDto changeUserStatus(String email, Long levelChange, Long expChange){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));
        UserStatus userStatusFound = userStatusRepository.findById(user.getId()).orElseThrow(()->new UserException(ErrorCode.USER_STATUS_NOT_EXIST));
        userStatusFound.setLevel(userStatusFound.getLevel()+levelChange);
        userStatusFound.setExp(userStatusFound.getExp() + expChange);

//        UserStatus newUserStatus = UserStatus.builder()
//                .user(user)
//                .level(userStatusFound.getLevel()+levelChange)
//                .exp(userStatusFound.getExp() + expChange)
//                .build();
        UserStatus userStatus = userStatusRepository.save(userStatusFound);

        return UserStatusResponseDto.builder()
                .userId(userStatus.getId())
                .exp(userStatus.getExp())
                .level(userStatus.getLevel())
                .build();
    }
}
