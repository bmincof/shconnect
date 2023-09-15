package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.request.TributeRegistRequest;
import com.shinhan.connector.dto.response.TributeResponse;
import com.shinhan.connector.service.TributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tribute")
@RequiredArgsConstructor
public class TributeController {
    private final TributeService tributeService;
    @PostMapping
    public ResponseEntity<TributeResponse> addTribute(@RequestBody TributeRegistRequest tributeRegistRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tributeService.regist(tributeRegistRequest, user));
    }

    @GetMapping("/{tributeNo}")
    public ResponseEntity<TributeResponse> getDetail(@RequestParam String option, @PathVariable Integer tributeNo) {
        return ResponseEntity.status(HttpStatus.OK).body(tributeService.getDetail(option, tributeNo             ));
    }
}
