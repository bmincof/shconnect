package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.SavingsLetterAddRequest;
import com.shinhan.connector.dto.request.SavingsLetterModifyRequest;
import com.shinhan.connector.dto.response.SavingsLetterResponse;
import com.shinhan.connector.service.SavingsLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/savings-letter")
@RequiredArgsConstructor
public class SavingsLetterController {
    private final SavingsLetterService savingsLetterService;
    @PostMapping
    public ResponseEntity<SavingsLetterResponse> registSavingsLetter(@RequestBody SavingsLetterAddRequest savingsLetterAddRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savingsLetterService.regist(savingsLetterAddRequest, user));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SavingsLetterResponse>> getList(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(savingsLetterService.getAll(user));
    }


    @GetMapping("/{savingsLetterNo}")
    public ResponseEntity<SavingsLetterResponse> getDetails(@PathVariable Integer savingsLetterNo, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(savingsLetterService.getDetails(savingsLetterNo, user));
    }

    @PutMapping("/{savingsLetterNo}")
    public ResponseEntity<SavingsLetterResponse> modifyLetter(@PathVariable Integer savingsLetterNo, @RequestBody SavingsLetterModifyRequest savingsLetterModifyRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(savingsLetterService.modifyLetter(savingsLetterNo, savingsLetterModifyRequest, user));
    }

    @DeleteMapping("/{savingsLetterNo}")
    public ResponseEntity<ResponseMessage> deleteLetter(@PathVariable Integer savingsLetterNo, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(savingsLetterService.deleteLetter(savingsLetterNo, user));
    }
}
