package com.ureca.ocean.jjh.user.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.exception.ErrorCode;

import com.ureca.ocean.jjh.user.dto.request.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.request.UserStatusRequestDto;
import com.ureca.ocean.jjh.user.dto.response.AttendanceListResponseDto;
import com.ureca.ocean.jjh.user.dto.response.AttendanceResponseDto;
import com.ureca.ocean.jjh.user.dto.response.UserResponseDto;
import com.ureca.ocean.jjh.user.dto.response.UserStatusResponseDto;
import com.ureca.ocean.jjh.user.service.impl.AttendanceServiceImpl;
import com.ureca.ocean.jjh.user.service.impl.UserServiceImpl;
import com.ureca.ocean.jjh.user.service.impl.UserStatusServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserStatusServiceImpl userStatusServiceImpl;
    private final AttendanceServiceImpl attendanceServiceImpl;

    @PostMapping("/health")
    public String health() {
        log.info("user health checking...");
        return "user health check fine";
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> getUserByEmail(@RequestParam String email) {
        UserResponseDto userDto = userServiceImpl.getUserByEmail(email);
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.NOT_FOUND_USER));
        }
        log.info(userDto.getPassword());
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    @GetMapping("/isDupNickname")
    public ResponseEntity<BaseResponseDto<?>> getIsDupNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(BaseResponseDto.success(userServiceImpl.getIsDupNickname(nickname)));
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto<?>> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto userDto = userServiceImpl.signUp(signUpRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    @PostMapping("/currentUserInfo")
    public ResponseEntity<BaseResponseDto<?>> getCurrentUserInfo(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserResponseDto userDto = userServiceImpl.getCurrentUserInfo(email);
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.NOT_FOUND_USER));
        }
        log.info(userDto.getPassword());
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    @GetMapping("/stat")
    public ResponseEntity<BaseResponseDto<?>> getUserStatus(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserStatusResponseDto userStatusResponseDto = userStatusServiceImpl.getUserStatus(email);
        return ResponseEntity.ok(BaseResponseDto.success(userStatusResponseDto));
    }

    @PutMapping("/stat")
    public ResponseEntity<BaseResponseDto<?>> modifyUserStatus(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
            @RequestBody UserStatusRequestDto userStatusRequestDto) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserStatusResponseDto userStatusResponseDto = userStatusServiceImpl.changeUserStatus(
                email, userStatusRequestDto.getLevelChange(), userStatusRequestDto.getExpChange());
        return ResponseEntity.ok(BaseResponseDto.success(userStatusResponseDto));
    }

    @PostMapping("/attendance")
    public ResponseEntity<BaseResponseDto<?>> insertAttendance(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        AttendanceResponseDto userStatusResponseDto = attendanceServiceImpl.insertAttendance(email);
        return ResponseEntity.ok(BaseResponseDto.success(userStatusResponseDto));
    }

    @GetMapping("/attendance")
    public ResponseEntity<BaseResponseDto<?>> listAttendance(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
            @RequestParam Integer year,
            @RequestParam Integer month) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        AttendanceListResponseDto attendanceListResponseDto = attendanceServiceImpl.listAttendance(email, year, month);
        return ResponseEntity.ok(BaseResponseDto.success(attendanceListResponseDto));
    }
}
