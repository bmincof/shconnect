package com.shinhan.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftLog {
    @Id @GeneratedValue
    @Column(name = "gift_log_no")
    private Integer no;
    @Column(name = "age_range")
    private Integer ageRange;
    @Column(length = 20, nullable = false)
    private String category;
    @Column(name = "gift_category", length = 20, nullable = false)
    private String giftCategory;
    @Column(name = "avg_price")
    private Long avgPrice;
    @Column
    private Integer count;
    @Column
    private String gender;

    // 로그 수정
    public GiftLog update(GiftSend giftSend) {
        int newCount = this.count + 1;

        this.avgPrice = (this.avgPrice * this.count + giftSend.getPrice()) / newCount;
        this.count = newCount;

        return this;
    }
}
