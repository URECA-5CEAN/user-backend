package com.ureca.ocean.jjh.mission.service.impl;

import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.dto.MyMissionDto;
import com.ureca.ocean.jjh.mission.entity.Mission;
import com.ureca.ocean.jjh.mission.entity.MissionCondition;
import com.ureca.ocean.jjh.mission.entity.UserMission;
import com.ureca.ocean.jjh.mission.repository.MissionRepository;
import com.ureca.ocean.jjh.mission.service.MissionService;
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

    @Override
    public List<MissionWithConditionDto> getAllMissions() {
        return missionRepository.findAllWithConditions();
    }

    @Override
    public List<MyMissionDto> getMyMissions(String email, boolean completed) {
        UUID userId = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER))
            .getId();

        List<UserMission> userMissions = missionRepository.findUserMissionsByUserIdAndCompleted(userId, completed);

        if (userMissions == null || userMissions.isEmpty()) {
            throw new UserException(ErrorCode.NOT_FOUND_USER);
        }

        return userMissions.stream()
                .map(um -> {
                    Mission mission = um.getMission();
                    MissionCondition condition = mission.getConditions().stream().findFirst().orElse(null);
                    return MyMissionDto.from(mission, um, condition);
                })
                .toList();
    }
}
