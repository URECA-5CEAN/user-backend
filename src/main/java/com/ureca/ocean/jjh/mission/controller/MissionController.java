package com.ureca.ocean.jjh.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mission", description = "미션 관련 API")
@RequestMapping("api/user")
@RestController @RequiredArgsConstructor @Slf4j
public class MissionController {

    @Operation(summary = "내 미션 목록 조회", description = "[개발 중] 미션 목록을 가져온다.")
    @GetMapping("/mission")
    public String getMissions() {
        return "";
    }

    @Operation(summary = "미션 완료", description = "[개발 중] 미션 완료 처리한다.")
    @GetMapping("/mission/complete")
    public String getMissionComplete() {
        return "";
    }
}
