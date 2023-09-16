package com.shinhan.connector.entity;

import com.shinhan.connector.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "i_account_number", columnList = "account_number")})
public class Account {
    @Id @GeneratedValue
    @Column(name = "account_no")
    private Integer no;
    @Column(name = "account_holder")
    private String accountHolder;
    @Column(name = "bank_code")
    private String bankCode;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "remain_money")
    private Long remainMoney;
    private Long date;

    // 입출금, 예적금
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccountHistory> accountHistories;

    public void addOne() {
        this.remainMoney++;
    }

    public void sendMoney(Long amount) {
        if (this.remainMoney < amount) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "잔액이 부족합니다.");
        }

        this.remainMoney -= amount;
    }

    public void receiveMoney(Long amount) {
        this.remainMoney += amount;
    }
}
