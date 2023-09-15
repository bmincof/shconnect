package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.response.AccountHistoryResponse;
import com.shinhan.connector.dto.response.AccountResponse;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.SendMoneyRequest;
import com.shinhan.connector.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<List<AccountHistoryResponse>> getHistory(@PathVariable String accountNumber, String option, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(accountService.getHistory(accountNumber, option, user));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountResponse>> getAccountList(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(accountService.getAccountList(user));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber, user));
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getAccountHolder(@RequestParam(name = "account-number") String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountHolder(accountNumber));
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> sendMoney(@RequestBody SendMoneyRequest sendMoneyRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(accountService.sendMoney(sendMoneyRequest, user));
    }
}
