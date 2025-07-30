package com.ureca.ocean.jjh.user.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.exception.ErrorCode;

import com.ureca.ocean.jjh.user.dto.request.SignUpRequestDto;
import com.ureca.ocean.jjh.user.dto.request.UserRequestDto;
import com.ureca.ocean.jjh.user.dto.request.UserStatusRequestDto;
import com.ureca.ocean.jjh.user.dto.response.*;
import com.ureca.ocean.jjh.user.service.UserService;
import com.ureca.ocean.jjh.user.service.impl.AttendanceServiceImpl;
import com.ureca.ocean.jjh.user.service.impl.UserServiceImpl;
import com.ureca.ocean.jjh.user.service.impl.UserStatusServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserStatusServiceImpl userStatusServiceImpl;
    private final AttendanceServiceImpl attendanceServiceImpl;
    private final UserService userService;

    /**
     * Health check endpoint
     */
    @Operation(summary = "User service health check")
    @PostMapping("/health")
    public String health() {
        log.info("user health checking...");
        return "user health check fine";
    }

    /**
     * Get user info by email
     */
    @Operation(summary = "Get user info by email")
    @GetMapping
    public ResponseEntity<BaseResponseDto<?>> getUserByEmail(
            @Parameter(description = "User email") @RequestParam String email) {
        UserResponseDtoWithPassword userResponseDtoWithPassword = userServiceImpl.getUserByEmail(email);
        if (userResponseDtoWithPassword.getId() == null) {
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.NOT_FOUND_USER));
        }
        return ResponseEntity.ok(BaseResponseDto.success(userResponseDtoWithPassword));
    }

    /**
     * Check if nickname is duplicated
     */
    @Operation(summary = "Check duplicate nickname")
    @GetMapping("/isDupNickname")
    public ResponseEntity<BaseResponseDto<?>> getIsDupNickname(
            @Parameter(description = "Nickname to check") @RequestParam String nickname) {
        return ResponseEntity.ok(BaseResponseDto.success(userServiceImpl.getIsDupNickname(nickname)));
    }

    /**
     * User sign up
     */
    @Operation(summary = "User sign up")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto<?>> signUp(
            @Parameter(description = "Sign up request body") @RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto userDto = userServiceImpl.signUp(signUpRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    /**
     * Get current logged-in user info from header
     */
    @Operation(summary = "Get current user info")
    @PostMapping("/currentUserInfo")
    public ResponseEntity<BaseResponseDto<?>> getCurrentUserInfo(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserResponseDto userDto = userServiceImpl.getCurrentUserInfo(email);
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest().body(BaseResponseDto.fail(ErrorCode.NOT_FOUND_USER));
        }
        return ResponseEntity.ok(BaseResponseDto.success(userDto));
    }

    /**
     * Get user status
     */
    @Operation(summary = "Get user status")
    @GetMapping("/stat")
    public ResponseEntity<BaseResponseDto<?>> getUserStatus(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserStatusResponseDto userStatusResponseDto = userStatusServiceImpl.getUserStatus(email);
        return ResponseEntity.ok(BaseResponseDto.success(userStatusResponseDto));
    }

    /**
     * Modify user status
     */
    @Operation(summary = "Modify user status")
    @PutMapping("/stat")
    public ResponseEntity<BaseResponseDto<?>> modifyUserStatus(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
            @Parameter(description = "User status change request") @RequestBody UserStatusRequestDto userStatusRequestDto) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserStatusResponseDto userStatusResponseDto = userStatusServiceImpl.changeUserStatus(
                email, userStatusRequestDto.getLevelChange(), userStatusRequestDto.getExpChange());
        return ResponseEntity.ok(BaseResponseDto.success(userStatusResponseDto));
    }

    /**
     * Insert attendance record for user
     */
    @Operation(summary = "Insert attendance record")
    @PostMapping("/attendance")
    public ResponseEntity<BaseResponseDto<?>> insertAttendance(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        AttendanceResponseDto userStatusResponseDto = attendanceServiceImpl.insertAttendance(email);
        return ResponseEntity.ok(BaseResponseDto.success(userStatusResponseDto));
    }

    /**
     * List attendance records by year and month
     */
    @Operation(summary = "List attendance records by year and month")
    @GetMapping("/attendance")
    public ResponseEntity<BaseResponseDto<?>> listAttendance(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
            @Parameter(description = "Year (ex: 2025)") @RequestParam Integer year,
            @Parameter(description = "Month (1-12)") @RequestParam Integer month) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        AttendanceListResponseDto attendanceListResponseDto = attendanceServiceImpl.listAttendance(email, year, month);
        return ResponseEntity.ok(BaseResponseDto.success(attendanceListResponseDto));
    }

    /**
     * Update user information
     */
    @Operation(summary = "Update user information")
    @PutMapping
    public ResponseEntity<BaseResponseDto<?>> updateUserInfo(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail,
            @Parameter(description = "User info update request") @RequestBody UserRequestDto userRequestDto) {
        String email = URLDecoder.decode(encodedEmail, StandardCharsets.UTF_8);
        log.info("user-backend 내의 current userEmail : " + email);
        UserResponseDto userResponseDto = userServiceImpl.updateUserInfo(
                email,
                userRequestDto);
        return ResponseEntity.ok(BaseResponseDto.success(userResponseDto));
    }

    /**
     * Get User and Status By Email
     */
    @Operation(summary = "Get User and Status By Email")
    @GetMapping("status")
    public ResponseEntity<BaseResponseDto<?>> getUserAndStatusByEmail(
            @Parameter(description = "User email") @RequestParam String email
    ) {
        Optional<UserAndStatusResponseDto> userAndStatusResponse = userService.getUserAndStatusByEmail(email);

        return ResponseEntity.ok(BaseResponseDto.success(userAndStatusResponse));
    }
}
