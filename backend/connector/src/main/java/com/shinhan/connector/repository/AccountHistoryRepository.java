package com.shinhan.connector.repository;

import com.shinhan.connector.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> {
}
