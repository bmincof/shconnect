package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.FriendAddRequest;
import com.shinhan.connector.dto.FriendAddResponse;
import com.shinhan.connector.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendService friendService;
    @PostMapping
    public ResponseEntity<FriendAddResponse> createFriend(@RequestBody FriendAddRequest friendAddRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(friendService.createFriend(friendAddRequest, user));
    }
}
