package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.TributeRegistRequest;
import com.shinhan.connector.dto.TributeResponse;
import com.shinhan.connector.service.TributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tribute")
@RequiredArgsConstructor
public class TributeController {
    private final TributeService tributeService;
    @PostMapping
    public ResponseEntity<TributeResponse> addTribute(@RequestBody TributeRegistRequest tributeRegistRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tributeService.regist(tributeRegistRequest, user));
    }
}
