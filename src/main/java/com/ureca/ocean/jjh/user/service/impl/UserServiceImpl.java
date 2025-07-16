package com.ureca.ocean.jjh.user.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;

import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.dto.request.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.response.UserResponseDto;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.entity.UserStatus;
import com.ureca.ocean.jjh.user.entity.enums.Membership;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import com.ureca.ocean.jjh.user.repository.UserStatusRepository;
import com.ureca.ocean.jjh.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    @Override
    public UserResponseDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .build();
    }

    @Override
    @Transactional
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if( userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()){
            throw new UserException(ErrorCode.USER_ALREADY_EXIST);
        }
        //사용자 테이블 insert
        User user = new User();
        user.setName(signUpRequestDto.getName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setAddress("initial address");
        user.setGender(signUpRequestDto.getGender());
        user.setMembership(Membership.우수);
        user.setNickname(signUpRequestDto.getNickname());

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        //사용자 status 테이블 insert
        UserStatus userStatus = UserStatus.builder()
                .user(savedUser)
                .level(0)
                .exp(0)
                .build();
        try{
            userStatusRepository.save(userStatus);
        }catch(Exception e){
            throw new UserException(ErrorCode.USER_STATUS_SAVE_FAIL);
        }

        return UserResponseDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(null)  // password는 dto에서 뺌
                .build();
    }
    @Override
    public boolean getIsDupNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


    @Override
    public UserResponseDto getCurrentUserInfo(String email){
        UserResponseDto userDto = new UserResponseDto();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            userDto = UserResponseDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .name(user.getName())
                    .build();
        }

        return userDto;
    }
}
