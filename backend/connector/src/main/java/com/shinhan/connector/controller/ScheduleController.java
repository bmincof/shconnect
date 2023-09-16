package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.ScheduleAddRequest;
import com.shinhan.connector.dto.request.ScheduleUpdateRequest;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.response.ScheduleAddResponse;
import com.shinhan.connector.dto.response.ScheduleListResponse;
import com.shinhan.connector.dto.response.ScheduleResponse;
import com.shinhan.connector.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<List<ScheduleAddResponse>> addSchedule(@RequestBody ScheduleAddRequest request,
                                                           @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.addSchedule(request, user));
    }

    @DeleteMapping("/{scheduleNo}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable Integer scheduleNo,
                                                          @RequestParam(required = false) String option,
                                                          @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleNo, option, user));
    }

    // 일정 상세 조회
    @GetMapping("/{scheduleNo}")
    public ResponseEntity<ScheduleResponse> getScheduleDetail(@PathVariable Integer scheduleNo,
                                                              @RequestParam(required = false) String option,
                                                              @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(scheduleService.selectSchedule(scheduleNo, option, user));
    }

    // 일정의 선물, 경조사비 목록 조회
    @GetMapping("/{scheduleNo}/list")
    public ResponseEntity<?> getListOfSchedule(@PathVariable Integer scheduleNo,
                                               @RequestParam String type,
                                               @RequestParam(required = false) String option,
                                               @AuthenticationPrincipal UserDetailsImpl user) {
        if (type.equals("tribute")) {
            return ResponseEntity.ok(scheduleService.selectAllTributesBySchedule(scheduleNo, option, user));
        } else {
            return ResponseEntity.ok(scheduleService.selectAllGiftsBySchedule(scheduleNo, option, user));
        }
    }

    // 일정 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ScheduleListResponse>> getSchedules(SearchCondition searchCondition,
                                                                   @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(scheduleService.selectAllSchedule(searchCondition, user));
    }

    @PutMapping("/{scheduleNo}")
    public ResponseEntity<List<ScheduleResponse>> updateSchedule(@PathVariable Integer scheduleNo,
                                                           @RequestParam(required = false) String option,
                                                           @RequestBody ScheduleUpdateRequest updateRequest,
                                                           @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleNo, option, updateRequest, user));
    }
}
