package com.ureca.ocean.jjh.mission.service;

import com.ureca.ocean.jjh.mission.dto.UserMissionDto;

import java.util.List;
import java.util.UUID;

public interface MissionService {
    List<UserMissionDto> getAllMissions();
    List<UserMissionDto> getMissionsByUserId(UUID userId);
}
