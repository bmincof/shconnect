package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.AccountHistoryResponse;
import com.shinhan.connector.dto.AccountResponse;
import com.shinhan.connector.entity.Account;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.AccountRepository;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public List<AccountHistoryResponse> getHistory(String accountNumber, String option, UserDetailsImpl user) {
        log.info("[계좌내역 조회] 계좌내역조회 요청. {}, {}, {}", accountNumber, option, user);

        if (option != null && option.contains("give")) {
            log.info("[계좌내역 조회] 송금 내역 조회");
        } else if (option != null && option.contains("receive")){
            log.info("[계좌내역 조회] 입금 내역 조회");
        } else {
            log.info("[계좌내역 조회] 전체 내역 조회");
        }

        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> {
            log.error("[계좌내역 조회] 계좌를 찾을 수 없습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "계좌를 찾을 수 없습니다.");
        });

        Member member = memberRepository.findById(user.getId()).get();

        if (!member.getName().equals(account.getAccountHolder())) {
            log.error("[계좌내역 조회] 자신의 계좌만 조회 가능. {}", member.getName());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 계좌만 조회할 수 있습니다.");
        }

        log.info("[계좌내역 조회] 조회 완료");
        return account.getAccountHistories().stream()
                .filter((history -> {
                    if (option != null && option.contains("give")) {
                        return history.getModifiedAmount() < 0;
                    } else if (option != null && option.contains("receive")){
                        return history.getModifiedAmount() > 0;
                    } else {
                        return true;
                    }
                }))
                .map((history -> AccountHistoryResponse.entityToDto(history)))
                .sorted(Comparator.comparing(AccountHistoryResponse::getDate))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccountResponse> getAccountList(UserDetailsImpl user) {
        log.info("[계좌 목록 조회] 목록 조회 요청. {}", user);
        Member member = memberRepository.findById(user.getId()).get();

        log.info("[계좌 목록 조회] 목록 조회 완료");
        return accountRepository.findByAccountHolderOrderByTypeDesc(member.getName()).stream()
                .map(account -> AccountResponse.entityToDto(account))
                .collect(Collectors.toList());
    }

    public AccountResponse getAccount(String accountNumber, UserDetailsImpl user) {
        log.info("[계좌 조회] 계좌 조회 요청. {}", accountNumber, user);
        Member member = memberRepository.findById(user.getId()).get();

        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    log.error("[계좌 조회] 잘못된 계좌번호입니다.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 계좌정보입니다.");
                });

        if (!account.getAccountHolder().equals(member.getName())) {
            log.error("[계좌 조회] 자신의 계좌만 접근 가능합니다. {}", account.getAccountHolder());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 계좌만 접근 가능");
        }

        log.info("[계좌 조회] 계좌 조회 성공");
        return AccountResponse.entityToDto(account);
    }

    public Map<String, String> getAccountHolder(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    log.error("[계좌주 조회] 계좌를 찾을 수 없습니다.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "계좌를 찾을 수 없습니다.");
                });

        Map<String, String> map = new HashMap<>();
        map.put("accountHolder", account.getAccountHolder());

        log.info("[계좌주 조회] 계좌주 이름 반환. {}", account.getAccountHolder());
        return map;
    }
}
