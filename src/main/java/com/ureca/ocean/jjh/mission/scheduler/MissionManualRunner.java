package com.ureca.ocean.jjh.mission.scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MissionManualRunner implements CommandLineRunner {

    private final DailyMissionScheduler scheduler;

    @Override
    public void run(String... args) {
        scheduler.refreshDailyMissions();
    }
}

