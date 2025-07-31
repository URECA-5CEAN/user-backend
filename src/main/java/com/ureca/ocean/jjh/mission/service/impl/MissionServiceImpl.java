package com.ureca.ocean.jjh.mission.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.mission.dto.MissionCompleteDto;
import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.MyMissionDto;
import com.ureca.ocean.jjh.mission.entity.Mission;
import com.ureca.ocean.jjh.mission.entity.MissionCondition;
import com.ureca.ocean.jjh.mission.entity.UserMission;
import com.ureca.ocean.jjh.mission.repository.MissionConditionRepository;
import com.ureca.ocean.jjh.mission.repository.MissionRepository;
import com.ureca.ocean.jjh.mission.repository.UserMissionRepository;
import com.ureca.ocean.jjh.mission.service.MissionService;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {
    private final MissionConditionRepository missionConditionRepository;
    private final UserMissionRepository userMissionRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    @Override
    public List<MissionWithConditionDto> getAllMissions() {
        return missionRepository.findAllWithConditions();
    }

    @Override
    public List<MyMissionDto> getMyMissions(String email, Boolean completed) {
        UUID userId = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER))
            .getId();

        List<UserMission> userMissions = (completed == null)
            ? missionRepository.findUserMissionsByUserId(userId)
            : missionRepository.findUserMissionsByUserIdAndCompleted(userId, completed);

        return userMissions.stream()
                .map(um -> {
                    Mission mission = um.getMission();
                    MissionCondition condition = mission.getConditions().stream().findFirst().orElse(null);
                    return MyMissionDto.from(mission, um, condition);
                })
                .toList();
    }

    // ===================================================공사중===================================================
    @Override
    @Transactional
    public MissionCompleteDto getMissionComplete(String email, UUID missionId) {
        UUID userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER))
                .getId();

        UserMission userMission = userMissionRepository.getUserMissionsByUserId(userId).stream()
                .filter(um -> um.getMission().getId().equals(missionId))
                .findFirst()
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_MISSION));

        if (userMission.isCompleted()) {
            throw new UserException(ErrorCode.ALREADY_COMPLETED);
        }

        userMission.setCompleted(true);
        userMissionRepository.save(userMission);

        Mission mission = userMission.getMission();
        MissionCondition condition = mission.getConditions().stream().findFirst().orElse(null);

        return MissionCompleteDto.from(userMission, mission, condition);
    }
}
