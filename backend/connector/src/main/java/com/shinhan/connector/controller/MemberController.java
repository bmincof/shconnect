package com.shinhan.connector.controller;

import com.shinhan.connector.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest) {
        // 로그인 체크 메서드 + header에 JWT 담기
        return null;
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
