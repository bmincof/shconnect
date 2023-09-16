package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.GiftAddRequest;
import com.shinhan.connector.dto.request.GiftUpdateRequest;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.response.GiftAddResponse;
import com.shinhan.connector.dto.response.GiftResponse;
import com.shinhan.connector.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gift")
public class GiftController {
    private final GiftService giftService;

    @PostMapping
    public ResponseEntity<? extends GiftAddResponse> createGift(@RequestBody GiftAddRequest giftAddRequest,
                                                      @RequestParam String option,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(giftService.createGift(giftAddRequest, option, user));
    }

    @DeleteMapping("/{giftNo}")
    public ResponseEntity<ResponseMessage> deleteGift(@PathVariable Integer giftNo,
                                                      @RequestParam String option,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(giftService.deleteGift(giftNo, option, user));
    }

    @GetMapping("/{giftNo}")
    public ResponseEntity<? extends GiftResponse> getGift(@PathVariable Integer giftNo,
                                                          @RequestParam String option,
                                                          @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(giftService.getGift(giftNo, option, user));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllGift(SearchCondition searchCondition,
                                                                   @AuthenticationPrincipal UserDetailsImpl user) {
        if (searchCondition.getAmount()) {
            return ResponseEntity.ok(giftService.getTotalPrice(searchCondition, user));
        } else {
            return ResponseEntity.ok(giftService.getAllGift(searchCondition, user));
        }
    }

    @PutMapping("/{giftNo}")
    public ResponseEntity<? extends GiftResponse> modifyGift(@PathVariable Integer giftNo,
                                                             @RequestParam String option,
                                                             @RequestBody GiftUpdateRequest updateRequest,
                                                             @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(giftService.updateGift(giftNo, option, updateRequest, user));
    }
}
