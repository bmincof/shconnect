package com.shinhan.connector.service;

import com.shinhan.connector.entity.AccountHistory;
import com.shinhan.connector.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountHistoryService {
    private final AccountHistoryRepository accountHistoryRepository;
}
