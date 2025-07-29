package com.ureca.ocean.jjh.mission.service.impl;

import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.UserMissionDto;
import com.ureca.ocean.jjh.mission.repository.MissionConditionRepository;
import com.ureca.ocean.jjh.mission.repository.MissionRepository;
import com.ureca.ocean.jjh.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    private final MissionConditionRepository missionConditionRepository;

    @Override
    public List<MissionWithConditionDto> getAllMissions() {
        return missionRepository.findAllWithConditions();
    }

    @Override
    public List<UserMissionDto> getMissionsByUserId(UUID userId) {
        return List.of();
    }
}
