package com.ureca.ocean.jjh.mission.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.UserMissionDto;
import com.ureca.ocean.jjh.mission.entity.MissionCondition;
import com.ureca.ocean.jjh.mission.entity.UserMission;
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
        List<UserMission> userMissions = missionRepository.findAllUserMissions();

        if (userMissions == null || userMissions.isEmpty()) {
            throw new UserException(ErrorCode.NOT_FOUND_USER);
        }

        List<MissionCondition> conditions = missionConditionRepository.findAllByMissionIdIn(
            userMissions.stream()
                .map(userMission -> userMission.getMission().getId())
                .distinct()
                .toList()
        );

        return userMissions.stream()
            .map(userMission -> {
                MissionCondition matched = conditions.stream()
                    .filter(c -> c.getMission().getId().equals(userMission.getMission().getId()))
                    .findFirst()
                    .orElse(null);
                return MissionWithConditionDto.from(userMission.getMission(), matched);
            })
            .toList();
    }

    @Override
    public List<UserMissionDto> getMissionsByUserId(UUID userId) {
        return List.of();
    }
}
