package com.ureca.ocean.jjh.mission.service;

import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.MyMissionDto;

import java.util.List;

public interface MissionService {
    List<MissionWithConditionDto> getAllMissions();
    List<MyMissionDto> getMyMissions(String email, boolean completed);
}
