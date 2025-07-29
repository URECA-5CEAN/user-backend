package com.ureca.ocean.jjh.mission.repository;

import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Repository
public interface MissionRepository extends JpaRepository<Mission, UUID> {
    @Query("SELECT new com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto(" +
           "m.id, m.name, m.description, m.expReward, " +
           "mc.id, mc.requireType, mc.requireValue) " +
           "FROM Mission m JOIN MissionCondition mc ON m.id = mc.mission.id")
    List<MissionWithConditionDto> findAllWithConditions();
}
