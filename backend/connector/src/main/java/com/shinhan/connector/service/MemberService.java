package com.shinhan.connector.service;

import com.google.gson.Gson;
import com.shinhan.connector.config.jwt.JwtUtils;
import com.shinhan.connector.config.jwt.Token;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Value("${shb.url}")
    private String bankUrl;
    @Value("${shb.api_key}")
    private String apiKey;

    public TokenAndMemberResponse signIn(SignInRequest signInRequest) {
        log.info("[로그인] 로그인 요청, {}", signInRequest.toString());

        Member member = memberRepository.findMemberById(signInRequest.getId())
                .orElseThrow(() -> {
                    log.error("[로그인] 멤버를 찾을 수 없습니다. {}", signInRequest.getId());
                    return new NoSuchElementException();
                });

        if(!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            log.error("[로그인] 비밀번호 불일치, {}", signInRequest.getPassword());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        log.info("[로그인] JWT 발급 시작");
        Token token = jwtUtils.generateAccessAndRefreshTokens(authenticationManager, member.getId(), member.getName());

        log.info("[로그인] 로그인 완료");
        return TokenAndMemberResponse.builder()
                .token(token)
                .signInResponse(SignInResponse.entityToDto(member))
                .build();
    }

    public boolean duplicationCheck(String memberId) {
        log.info("[아이디 중복체크] 아이디 : {}", memberId);
        return memberRepository.existsMemberById(memberId);
    }

    public ResponseMessage signUp(SignUpRequest signUpRequest) {
        log.info("[회원가입] 회원가입 요청. {}", signUpRequest.toString());

        if (duplicationCheck(signUpRequest.getId())) {
            log.error("[회원가입] 이미 가입된 아이디입니다. {}", signUpRequest.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 있는 아이디입니다.");
        }

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        log.info("[회원가입] 비밀번호 암호화 성공");

        try {
            memberRepository.save(signUpRequest.toEntity());
            memberRepository.flush();
        } catch (Exception e) {
            log.error("[회원가입] 회원가입 저장 실패");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        log.info("[회원가입] 회원가입 완료");
        return new ResponseMessage("회원가입 성공");
    }

    public ResponseMessage transfer1(TransferOneRequest transferOneRequest) {
        log.info("[1원송금] 1원송금 요청. {}", transferOneRequest.toString());

        try {
            URL url = new URL(bankUrl + "/v1/auth/1transfer");

            log.info("[1원 송금] 헤더 설정");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);

            log.info("[1원 송금] 바디 설정");
            Map<String, Object> shbRequest = new HashMap<>();
            shbRequest.put("dataHeader", new BankHeader(apiKey));
            shbRequest.put("dataBody", TransferPostRequest.builder()
                    .입금계좌번호(transferOneRequest.getAccountNumber())
                    .입금은행코드(transferOneRequest.getBankCode())
                    .입금통장메모("1234").build());

            Gson gson = new Gson();
            String requestBody = gson.toJson(shbRequest);

            log.info("[1원 송금] JSON 객체 바꾸기. {}", requestBody);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new ResponseMessage("1원이 송금되었습니다.");
            } else {
                throw new RuntimeException("HTTP 요청 실패. 응답 코드: " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
