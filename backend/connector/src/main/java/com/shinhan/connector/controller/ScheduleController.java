package com.shinhan.connector.controller;

import com.shinhan.connector.dto.ScheduleAddRequest;
import com.shinhan.connector.dto.ScheduleAddResponse;
import com.shinhan.connector.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleAddResponse> addSchedule(ScheduleAddRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.addSchedule(request));
    }
}
