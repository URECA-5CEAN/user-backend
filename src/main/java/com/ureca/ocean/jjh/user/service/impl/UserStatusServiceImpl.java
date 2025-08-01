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
    public UserStatusResponseDto changeUserStatus(String email, Long expChange){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));
        UserStatus userStatusFound = userStatusRepository.findById(user.getId()).orElseThrow(()->new UserException(ErrorCode.USER_STATUS_NOT_EXIST));

        Long beforeLevel = userStatusFound.getLevel();

        Long beforeExp = userStatusFound.getExp();
        Long afterExp = beforeExp + expChange;

//        Long expBound = ((beforeExp/50)+1) * 50;

        boolean levelChanged = false;
        Long afterLevel = 0L;
        if(afterExp/50 > 0){
             afterLevel = beforeLevel + afterExp/50;
        }else{
             afterLevel = beforeLevel + afterExp/50 - 1;
        }

        userStatusFound.setLevel(afterLevel);
        if(!afterLevel.equals(beforeLevel)) levelChanged=true;

        if(afterExp >= 0){
            afterExp = afterExp % 50;
        }else{
            if(afterExp%50 ==0){
                afterExp = 0L;
            }else{
                afterExp = 50 + afterExp % 50;
            }
        }

        userStatusFound.setExp(afterExp);


        UserStatus userStatus = userStatusRepository.save(userStatusFound);

        return UserStatusResponseDto.builder()
                .userId(userStatus.getId())
                .exp(userStatus.getExp())
                .level(userStatus.getLevel())
                .isLevelUpdated(levelChanged)
                .build();
    }

}
