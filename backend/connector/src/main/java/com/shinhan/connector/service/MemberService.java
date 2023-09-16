package com.shinhan.connector.service;

import com.google.gson.Gson;
import com.shinhan.connector.config.jwt.JwtUtils;
import com.shinhan.connector.config.jwt.Token;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.dto.request.SignInRequest;
import com.shinhan.connector.dto.request.SignUpRequest;
import com.shinhan.connector.dto.request.TransferOneCheckRequest;
import com.shinhan.connector.dto.request.TransferOneRequest;
import com.shinhan.connector.dto.response.SignInResponse;
import com.shinhan.connector.dto.response.TokenAndMemberResponse;
import com.shinhan.connector.entity.Account;
import com.shinhan.connector.entity.AccountHistory;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.enums.Gender;
import com.shinhan.connector.repository.AccountHistoryRepository;
import com.shinhan.connector.repository.AccountRepository;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private Map<String, String> transfer1Check = new HashMap<>();
    private Set<String> transferChecked = new HashSet<>();
    private final String CHECK_NUMBER = "1234";
    private Gson gson = new Gson();

    public TokenAndMemberResponse signIn(SignInRequest signInRequest) {
        log.info("[로그인] 로그인 요청, {}", signInRequest.toString());

        Member member = memberRepository.findMemberById(signInRequest.getId())
                .orElseThrow(() -> {
                    log.error("[로그인] 멤버를 찾을 수 없습니다. {}", signInRequest.getId());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디가 잘못되었습니다.");
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

    @Transactional
    public ResponseMessage signUp(SignUpRequest signUpRequest) {
        log.info("[회원가입] 회원가입 요청. {}", signUpRequest.toString());

        if (duplicationCheck(signUpRequest.getId())) {
            log.error("[회원가입] 이미 가입된 아이디입니다. {}", signUpRequest.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 있는 아이디입니다.");
        }

        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        log.info("[회원가입] 비밀번호 암호화 성공");

        if(!transferChecked.contains(signUpRequest.getAccountNumber())) {
            log.error("[회원가입] 계좌 인증이 필요합니다. {}", signUpRequest.getAccountNumber());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "계좌 인증이 필요합니다.");
        }
        transferChecked.remove(signUpRequest.getAccountNumber());

        memberRepository.save(Member.builder()
                        .id(signUpRequest.getId())
                        .password(signUpRequest.getPassword())
                        .gender(Gender.getGender(signUpRequest.getGender()))
                        .age(signUpRequest.getAge())
                        .name(signUpRequest.getName())
                        .contact(signUpRequest.getContact())
                        .account(accountRepository.findAccountByAccountNumber(signUpRequest.getAccountNumber()).orElseThrow(() -> {
                            log.error("[회원가입] 계좌를 찾을 수 없습니다.");
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "계좌를 찾을 수 없습니다.");
                        }))
                        .build());

        log.info("[회원가입] 회원가입 완료");
        return new ResponseMessage("회원가입 성공");
    }

    @Transactional
    public ResponseMessage transfer1(TransferOneRequest transferOneRequest) {
        log.info("[1원송금] 1원송금 요청. {}", transferOneRequest.toString());

        Account account = accountRepository.findAccountByAccountNumber(transferOneRequest.getAccountNumber())
                .orElseThrow(() -> {
                    log.error("[1원송금] 잘못된 계좌입니다. {}", transferOneRequest.getAccountNumber());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 계좌입니다.");
                });
        // 1원 추가
        account.addOne();
        accountHistoryRepository.save(AccountHistory.builder()
                        .date(System.currentTimeMillis() / 1000)
                        .depositorName(CHECK_NUMBER)
                        .accountNumber("신한은행인증계좌")
                        .bankCode("088")
                        .modifiedAmount(1L)
                        .remainAmount(account.getRemainMoney())
                        .note("회원가입 인증")
                        .build());

        log.info("[1원송금] 송금완료.");
        transfer1Check.put(transferOneRequest.getBankCode() + transferOneRequest.getAccountNumber(), CHECK_NUMBER);

        return new ResponseMessage("1원이 송금되었습니다.");
    }

    public ResponseMessage transfer1Check(TransferOneCheckRequest transferOneCheckRequest) {
        log.info("[1원인증 체크] 1원인증 체크 시작");
        String key = transferOneCheckRequest.getBankCode() + transferOneCheckRequest.getAccountNumber();

        if (!transfer1Check.containsKey(key)) {
            log.error("[1원인증 체크] 인증요청하지 않은 계좌입니다. {}", transferOneCheckRequest.getAccountNumber());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "인증요청되지 않은 계좌입니다.");
        }

        if (!transfer1Check.get(key).equals(transferOneCheckRequest.getConfirm())) {
            log.error("[1원인증 체크] 인증번호가 일치하지 않습니다. {}", transferOneCheckRequest.getConfirm());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "인증번호가 일치하지 않습니다.");
        }
        transfer1Check.remove(key);
        transferChecked.add(transferOneCheckRequest.getAccountNumber());

        return new ResponseMessage("인증되었습니다.");
    }
}
