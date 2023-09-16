package com.shinhan.connector.service;

import com.shinhan.connector.config.TimeUtils;
import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.response.AccountHistoryResponse;
import com.shinhan.connector.dto.response.AccountResponse;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.SendMoneyRequest;
import com.shinhan.connector.entity.*;
import com.shinhan.connector.enums.AccountType;
import com.shinhan.connector.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final SavingsLetterRepository savingsLetterRepository;
    private final MemberRepository memberRepository;
    private final TributeSendRepository tributeSendRepository;
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

    @Transactional
    public ResponseMessage sendMoney(SendMoneyRequest sendMoneyRequest, UserDetailsImpl user) {
        Member member = memberRepository.findById(user.getId()).get();

        Account receiveAccount = accountRepository.findAccountByAccountNumber(sendMoneyRequest.getAccountNumber())
                .orElseThrow(() -> {
                    log.error("[계좌 송금] 잘못된 계좌입니다.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 계좌번호입니다.");
                });

        Account giveAccount = member.getAccount();

        giveAccount.sendMoney(sendMoneyRequest.getAmount());
        receiveAccount.receiveMoney(sendMoneyRequest.getAmount());

        AccountHistory giveHistory = AccountHistory.builder()
                // 여기로 얼마를 보냄 (상대방 정보)
                .bankCode(receiveAccount.getBankCode())
                .accountNumber(receiveAccount.getAccountNumber())
                .modifiedAmount(-sendMoneyRequest.getAmount())
                .depositorName(receiveAccount.getAccountHolder())
                // 내 잔액과 송금한 시각
                .account(giveAccount)
                .remainAmount(giveAccount.getRemainMoney())
                .date(System.currentTimeMillis() / 1000)
                .build();

        AccountHistory receiveHistory = AccountHistory.builder()
                // 여기서 얼마를 받음 (상대방 정보)
                .bankCode(giveAccount.getBankCode())
                .accountNumber(giveAccount.getAccountNumber())
                .modifiedAmount(sendMoneyRequest.getAmount())
                .depositorName(sendMoneyRequest.getDepositorName())
                // 내 잔액과 송금받은 시각
                .account(receiveAccount)
                .remainAmount(receiveAccount.getRemainMoney())
                .date(giveHistory.getDate())
                .build();

        accountRepository.save(giveAccount);
        accountRepository.save(receiveAccount);

        // 경조사비 입금 처리
        if (sendMoneyRequest.getTributeNo() != null) {
            TributeSend tributeSend = tributeSendRepository.findById(sendMoneyRequest.getTributeNo())
                    .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 경조사비 번호입니다.")
                    );
            if (tributeSend.getSent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 송금이 완료된 내역입니다.");
            }
            tributeSend.send(sendMoneyRequest.getAmount());
            tributeSendRepository.save(tributeSend);
        }

        // 적금편지 입금 처리
        if (sendMoneyRequest.getSavingsLetterNo() != null) {
            SavingsLetter savingsLetter = savingsLetterRepository.findById(sendMoneyRequest.getSavingsLetterNo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "적금편지를 찾을 수 없습니다."));

            savingsLetter.send();
            savingsLetterRepository.save(savingsLetter);
        }

        accountHistoryRepository.save(giveHistory);
        accountHistoryRepository.save(receiveHistory);

        return new ResponseMessage("송금이 완료되었습니다.");
    }
}
