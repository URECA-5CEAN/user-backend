package com.ureca.ocean.jjh.mission.controller;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.mission.dto.MissionWithConditionDto;
import com.ureca.ocean.jjh.mission.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Mission", description = "미션 관련 API")
@RequestMapping("api/user")
@RestController @RequiredArgsConstructor @Slf4j
public class MissionController {
    private final MissionService missionService;

    @Operation(summary = "모든 미션 목록 조회", description = "[개발 중] 모든 미션 목록을 가져온다.")
    @GetMapping("/mission")
    public ResponseEntity<BaseResponseDto<?>> getMissions() {
        List<MissionWithConditionDto> userMissionDto = missionService.getAllMissions();
        log.info("모든 미션 목록 조회");
        return ResponseEntity.ok(BaseResponseDto.success(userMissionDto));
    }

    @Operation(summary = "내 미션 목록 조회", description = "[개발 중] 로그인 된 계정의 미션 목록을 가져온다.")
    @GetMapping("/mission/my")
    public ResponseEntity<BaseResponseDto<?>> getMissions(
            @Parameter(hidden = true) @RequestHeader("X-User-email") String encodedEmail
    ) {
//        List<UserMissionDto> userMissionDto = missionService.getAllMissions();
//        log.info(" 내 미션 목록 조회");
//        return ResponseEntity.ok(BaseResponseDto.success("내 미션 목록 조회 성공"));
        return ResponseEntity.ok(BaseResponseDto.success(""));
    }

    @Operation(summary = "미션 완료", description = "[개발 중] 미션 완료 처리한다.")
    @GetMapping("/mission/complete")
    public ResponseEntity<BaseResponseDto<?>> getMissionComplete() {
        return ResponseEntity.ok(BaseResponseDto.success(""));
    }
}