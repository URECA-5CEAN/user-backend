package com.ureca.ocean.jjh.user.repository;

import com.ureca.ocean.jjh.user.entity.Attendance;
import com.ureca.ocean.jjh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    boolean existsByDateAndUser(LocalDate today, User user);
}
