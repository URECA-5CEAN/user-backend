package com.ureca.ocean.jjh.mission.repository;

import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.entity.Mission;
import com.ureca.ocean.jjh.mission.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MissionRepository extends JpaRepository<Mission, UUID> {
    List<UserMission> findAllUserMissions();
    List<UserMission> findAllUserMissionsDtoByUserId(UUID userId);
}
