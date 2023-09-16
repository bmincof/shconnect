package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.request.RecommendCondition;
import com.shinhan.connector.dto.response.GiftRecommendResponse;
import com.shinhan.connector.dto.response.TributeRecommendResponse;
import com.shinhan.connector.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService recommendService;

    @GetMapping("/gift")
    public ResponseEntity<GiftRecommendResponse> recommendGift(RecommendCondition recommendCondition,
                                                               @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(recommendService.recommendGift(recommendCondition, user));
    }

    @GetMapping("/tribute")
    public ResponseEntity<TributeRecommendResponse> recommendTribute(RecommendCondition recommendCondition,
                                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(recommendService.recommendTribute(recommendCondition, user));
    }
}
