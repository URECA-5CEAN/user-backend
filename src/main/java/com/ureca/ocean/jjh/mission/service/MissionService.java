package com.ureca.ocean.jjh.mission.service;

import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.UserMissionDto;

import java.util.List;
import java.util.UUID;

public interface MissionService {
    List<MissionWithConditionDto> getAllMissions();
    List<UserMissionDto> getMissionsByUserId(UUID userId);
}
