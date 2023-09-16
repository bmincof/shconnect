package com.shinhan.connector.entity;

import com.shinhan.connector.config.TimeUtils;
import com.shinhan.connector.dto.request.SavingsLetterModifyRequest;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.StringTokenizer;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsLetter {
    @Id
    @GeneratedValue
    @Column(name = "savings_letter_no")
    private Integer no;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @Column(nullable = false)
    private String bankCode;
    @Column(nullable = false)
    private String bankAccount;
    @Column(nullable = false)
    private Integer currentRound;
    @Column(nullable = false)
    private Integer totalRound;
    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false)
    private Long paymentDate;
    @Column(nullable = false)
    private Long startDate;
    @Column(nullable = false)
    private Long endDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;

    public void send() {
        if (this.totalRound <= this.currentRound) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 만기된 적금편지입니다.");
        }

        this.currentRound++;
        this.paymentDate = TimeUtils.DATE_TO_UNIX(TimeUtils.UNIX_TO_DATE(paymentDate).plusMonths(1));
    }

    public void update(SavingsLetterModifyRequest savingsLetterModifyRequest) {
        this.name = savingsLetterModifyRequest.getName() == null ? name : savingsLetterModifyRequest.getName();
        this.amount = savingsLetterModifyRequest.getAmount() == null ? amount : savingsLetterModifyRequest.getAmount();
        this.paymentDate = savingsLetterModifyRequest.getPaymentDate() == null ? paymentDate : savingsLetterModifyRequest.getPaymentDate();
        this.endDate = savingsLetterModifyRequest.getEndDate() == null ? endDate : savingsLetterModifyRequest.getEndDate();

        if (savingsLetterModifyRequest.getContent() == null) return;

        String[] modifiedContent = savingsLetterModifyRequest.getContent().split("\n");

        int length = modifiedContent.length;

        if (length > this.totalRound) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "총 회차 이상의 값을 입력할 수 없습니다.");
        }

        StringTokenizer st = new StringTokenizer(content, "\\n");
        for (int i = 0; i < length; i++) {
            if (!st.hasMoreTokens()) continue;
            if (i == currentRound) break;
            if (st.nextToken().equals(modifiedContent[i])) continue;

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이전 회차 메시지를 수정할 수 없습니다.");
        }
        this.content = savingsLetterModifyRequest.getContent().replaceAll("\n", "\\\\n");
    }
}
