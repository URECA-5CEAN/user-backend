package com.ureca.ocean.jjh.mission.repository;

import com.ureca.ocean.jjh.mission.entity.MissionCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MissionConditionRepository extends JpaRepository<MissionCondition, UUID> {
    List<MissionCondition> findAllByMissionIdIn(List<UUID> missionId);
}
