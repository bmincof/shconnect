package com.shinhan.connector.controller;

import com.google.gson.Gson;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.dto.request.*;
import com.shinhan.connector.dto.response.SignInResponse;
import com.shinhan.connector.dto.response.TokenAndMemberResponse;
import com.shinhan.connector.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<TokenAndMemberResponse> signIn(@RequestBody SignInRequest signInRequest) {
        // 로그인 체크 메서드 + header에 JWT 담기
        TokenAndMemberResponse tokenAndMemberResponse = memberService.signIn(signInRequest);

        return ResponseEntity.status(HttpStatus.OK).body(tokenAndMemberResponse);
    }

    // 1원 송금
    @PostMapping("/1transfer")
    public ResponseEntity<ResponseMessage> transferOne(@RequestBody TransferOneRequest transferOneRequest) {
        return ResponseEntity.ok(memberService.transfer1(transferOneRequest));
    }

    // 1원 송금 체크
    @PostMapping("/1transfer/check")
    public ResponseEntity<ResponseMessage> transferOne(@RequestBody TransferOneCheckRequest transferOneCheckRequest) {
        return ResponseEntity.ok(memberService.transfer1Check(transferOneCheckRequest));
    }
    
    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.signUp(signUpRequest));
    }

    // 아이디 중복체크
    @PostMapping("/check")
    public ResponseEntity<ResponseResult> duplicationCheck(@RequestBody DuplicationCheckRequest duplicationCheckRequest) {
        return ResponseEntity.ok(new ResponseResult(memberService.duplicationCheck(duplicationCheckRequest.getId())));
    }
}
