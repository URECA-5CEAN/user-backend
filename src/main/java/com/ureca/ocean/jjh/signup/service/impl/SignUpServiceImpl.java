package com.ureca.ocean.jjh.signup.service.impl;

import com.ureca.ocean.jjh.signup.dto.SignUpRequestDto;
import com.ureca.ocean.jjh.signup.service.SignUpService;

import com.ureca.ocean.jjh.user.dto.UserDto;
import com.ureca.ocean.jjh.user.dto.UserResultDto;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpServiceImpl implements SignUpService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserResultDto signUp(SignUpRequestDto signUpRequestDto) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            Optional<User> optionalUser = userRepository.findByEmail(signUpRequestDto.getEmail());

            if( optionalUser.isPresent()  ) {
                userResultDto.setResult("exist");
                return userResultDto;
            }

            User user = new User();
            log.info(signUpRequestDto.getPassword());
            user.setName(signUpRequestDto.getName());
            user.setEmail(signUpRequestDto.getEmail());
            user.setAddress(signUpRequestDto.getAddress());
            user.setGender(signUpRequestDto.getGender());
            user.setMembership(signUpRequestDto.getMembership());
            user.setNickname(signUpRequestDto.getNickname());
            String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
            user.setPassword(encodedPassword);

            User savedUser = userRepository.save(user);
            userResultDto.setResult("success");

            userResultDto.setUserDto(
                    UserDto.builder()
                            .id(savedUser.getId())
                            .name(savedUser.getName())
                            .email(savedUser.getEmail())
                            .password(null)  // password는 dto에서 뺌
                            .build()
            );

        }catch( Exception e ) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }
}
