package com.ureca.ocean.jjh.mission.dto;

import com.ureca.ocean.jjh.mission.entity.Mission;
import com.ureca.ocean.jjh.mission.entity.MissionCondition;
import com.ureca.ocean.jjh.mission.entity.UserMission;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserMissionDto {
    private UUID id;
    private UUID userId;

    private boolean completed;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;

    private UUID missionId;
    private String name;
    private String description;
    private int expReward;

    private UUID missionConditionId;
    private String requireType;
    private int requireValue;

    // UserMissions → UserMissionsDto 변환용 정적 메서드
    public static UserMissionDto from(Mission mission, UserMission userMission, MissionCondition missionCondition) {
        return UserMissionDto.builder()
                .id(userMission.getId())
                .userId(userMission.getUserId())
                .completed(userMission.isCompleted())
                .completedAt(userMission.getCompletedAt())
                .createdAt(userMission.getCreatedAt())
                .missionId(userMission.getMission().getId())
                .name(mission.getName())
                .description(mission.getDescription())
                .expReward(mission.getExpReward())
                .missionConditionId(missionCondition.getId())
                .requireValue(missionCondition.getRequireValue())
                .requireValue(missionCondition.getRequireValue())
                .build();
    }
}
