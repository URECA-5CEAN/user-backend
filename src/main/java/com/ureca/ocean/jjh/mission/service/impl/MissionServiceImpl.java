package com.ureca.ocean.jjh.mission.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.MyMissionDto;
import com.ureca.ocean.jjh.mission.entity.Mission;
import com.ureca.ocean.jjh.mission.entity.MissionCondition;
import com.ureca.ocean.jjh.mission.entity.UserMission;
import com.ureca.ocean.jjh.mission.repository.MissionRepository;
import com.ureca.ocean.jjh.mission.repository.UserMissionRepository;
import com.ureca.ocean.jjh.mission.service.MissionService;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

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

    @Override
    public void completeMission(String email, UUID missionId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        UserMission userMission = userMissionRepository.findByUserIdAndMissionId(user.getId(), missionId)
                .orElseThrow(() -> new UserException(ErrorCode.MISSON_NOT_FOUND));

        userMission.setCompleted(true);

        userMissionRepository.save(userMission);
    }
}
