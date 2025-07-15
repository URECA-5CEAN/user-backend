package com.ureca.ocean.jjh.user.service.impl;

import com.ureca.ocean.jjh.common.exception.ErrorCode;

import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.UserResponseDto;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.entity.enums.Membership;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import com.ureca.ocean.jjh.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
