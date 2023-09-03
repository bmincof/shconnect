package com.shinhan.connector.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id @GeneratedValue
    @Column(name = "friend_no")
    private Integer no;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String contact;
    @Column(length = 20, nullable = false)
    private String relation;
    @Column(length = 50)
    private String belong;
    @Column(name = "bank_code", length = 10)
    private String bankCode;
    @Column(name = "account_number", length = 100)
    private String accountNumber;
    @Column(length = 100)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;
}
