package com.ureca.ocean.jjh.user.service;

import com.ureca.ocean.jjh.user.dto.response.AttendanceResponseDto;

public interface AttendanceService {

    AttendanceResponseDto insertAttendance(String email);
}
