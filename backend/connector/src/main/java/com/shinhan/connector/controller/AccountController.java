package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.AccountHistoryResponse;
import com.shinhan.connector.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<List<AccountHistoryResponse>> getHistory(@PathVariable String accountNumber, String option, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(accountService.getHistory(accountNumber, option, user));
    }
}
