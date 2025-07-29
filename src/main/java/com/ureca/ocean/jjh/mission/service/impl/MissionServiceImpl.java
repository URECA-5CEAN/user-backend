package com.ureca.ocean.jjh.mission.service.impl;

import com.ureca.ocean.jjh.mission.dto.UserMissionDto;
import com.ureca.ocean.jjh.mission.entity.UserMission;
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

    @Override
    public List<UserMissionDto> getAllMissions() {
        List<UserMission> userMissions = missionRepository.findALlUserMissions();
    }

    @Override
    public List<UserMissionDto> getMissionsByUserId(UUID userId) {
        return List.of();
    }
}
