package com.ureca.ocean.jjh.mission.repository;

import com.ureca.ocean.jjh.mission.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserMissionRepository extends JpaRepository<UserMission, UUID> {
    Optional<UserMission> findByUserIdAndMissionId(UUID userId, UUID missionId);
}
