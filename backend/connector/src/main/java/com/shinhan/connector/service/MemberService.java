package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.JwtUtils;
import com.shinhan.connector.config.jwt.Token;
import com.shinhan.connector.dto.SignInRequest;
import com.shinhan.connector.dto.SignInResponse;
import com.shinhan.connector.dto.TokenAndMemberResponse;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
}
