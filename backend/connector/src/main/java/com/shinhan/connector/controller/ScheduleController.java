package com.shinhan.connector.controller;

import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.ScheduleAddRequest;
import com.shinhan.connector.dto.ScheduleAddResponse;
import com.shinhan.connector.dto.ScheduleResponse;
import com.shinhan.connector.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{scheduleNo}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable Integer scheduleNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.deleteSchedule(scheduleNo));
    }

    // 일정 상세 조회
    @GetMapping("/{scheduleNo}")
    public ResponseEntity<ScheduleResponse> getScheduleDetail(@PathVariable Integer scheduleNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.selectSchedule(scheduleNo));
    }
}
