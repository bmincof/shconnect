package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.dto.request.FriendAddRequest;
import com.shinhan.connector.dto.request.FriendUpdateRequest;
import com.shinhan.connector.dto.response.FriendAddResponse;
import com.shinhan.connector.dto.response.FriendResponse;
import com.shinhan.connector.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<FriendResponse>> getFriends(@AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(friendService.getAll(user));
    }

    @GetMapping("/{friendNo}")
    public ResponseEntity<FriendResponse> getFriend(@PathVariable Integer friendNo, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(friendService.getFriend(friendNo, user));
    }

    @DeleteMapping("/{friendNo}")
    public ResponseEntity<ResponseMessage> removeFriend(@PathVariable Integer friendNo, @AuthenticationPrincipal UserDetailsImpl user) {
        friendService.removeFriend(friendNo, user);
        return ResponseEntity.ok(new ResponseMessage("삭제가 완료되었습니다."));
    }

    @PutMapping("/{friendNo}")
    public ResponseEntity<FriendResponse> updateFriends(@PathVariable Integer friendNo, @RequestBody FriendUpdateRequest friendUpdateRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(friendService.updateFriend(friendNo, friendUpdateRequest, user));
    }
}
