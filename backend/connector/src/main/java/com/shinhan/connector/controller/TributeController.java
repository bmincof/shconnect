package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.request.TributeModifyRequest;
import com.shinhan.connector.dto.request.TributeRegistRequest;
import com.shinhan.connector.dto.response.TributeResponse;
import com.shinhan.connector.service.TributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/list")
    public ResponseEntity<?> getList(SearchCondition searchCondition, @AuthenticationPrincipal UserDetailsImpl user) {
        System.out.println("searchCondition = " + searchCondition);
        if (searchCondition.getAmount() == null || searchCondition.getAmount()) {
            return ResponseEntity.ok(tributeService.getAmount(searchCondition, user));
        } else {
            return ResponseEntity.ok(tributeService.getList(searchCondition, user));
        }
    }

    @PutMapping("/{tributeNo}")
    public ResponseEntity<TributeResponse> modifyTribute(@RequestParam String option, @PathVariable Integer tributeNo, @RequestBody TributeModifyRequest tributeModifyRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(tributeService.modify(option, tributeNo, tributeModifyRequest, user));
    }

    @DeleteMapping("/{tributeNo}")
    public ResponseEntity<ResponseMessage> deleteTribute(@RequestParam String option, @PathVariable Integer tributeNo, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(tributeService.delete(option, tributeNo, user));
    }
}
