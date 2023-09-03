package com.shinhan.connector.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.Gson;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.service.MemberService;
import io.jsonwebtoken.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private Gson gson = new Gson();

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {
        // 로그인 체크 메서드 + header에 JWT 담기
        TokenAndMemberResponse tokenAndMemberResponse = memberService.signIn(signInRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .header("tokens", gson.toJson(tokenAndMemberResponse.getToken()))
                .header("Access-Control-Expose-Headers", "tokens")
                .body(tokenAndMemberResponse.getSignInResponse());
    }

    // 1원 송금
    @PostMapping("/1transfer")
    public ResponseEntity<ResponseMessage> transferOne(TransferOneRequest transferOneRequest) {
        // 1원 송금 로직
        return ResponseEntity.ok(new ResponseMessage("1원이 송금되었습니다."));
    }

    // 1원 송금 체크
    @PostMapping("/1transfer/check")
    public ResponseEntity<TransferOneCheckResponse> transferOne(TransferOneCheckRequest transferOneCheckRequest) {
        // 1원 송금 메시지 일치 여부 체크 로직
        return null;
    }
    
    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessage> signUp(SignUpRequest signUpRequest) {
        // 회원가입 메서드 로직
        return null;
    }
}
