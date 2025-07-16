package com.ureca.ocean.jjh.user.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.dto.response.AttendanceResponseDto;
import com.ureca.ocean.jjh.user.entity.Attendance;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.AttendanceRepository;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import com.ureca.ocean.jjh.user.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public AttendanceResponseDto insertAttendance(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));
        LocalDate today = LocalDate.now();

        if(attendanceRepository.existsByDateAndUser(today, user)){
            throw new UserException(ErrorCode.ATTENDANCE_ALREADY_DONE);
        }
        Attendance attendance = Attendance.builder()
                .date(today)
                .user(user)
                .build();

        Attendance attendanceResult = attendanceRepository.save(attendance);


        return AttendanceResponseDto.builder()
                .userId(attendanceResult.getUser().getId())
                .date(attendanceResult.getDate())
                .build();

    }
}

