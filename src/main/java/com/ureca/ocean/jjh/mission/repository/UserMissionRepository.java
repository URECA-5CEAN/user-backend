package com.ureca.ocean.jjh.mission.repository;

import com.ureca.ocean.jjh.mission.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, UUID> {
    List<UserMission> getUserMissionsByUserId(UUID userId);
}
