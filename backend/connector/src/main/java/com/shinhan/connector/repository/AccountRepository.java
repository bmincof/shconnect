package com.shinhan.connector.repository;

import com.shinhan.connector.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByAccountNumber(String accountNumber);

    List<Account> findByAccountHolderOrderByTypeDesc(String accountHolder);
}
