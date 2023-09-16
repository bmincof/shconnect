package com.shinhan.connector.entity;

import com.shinhan.connector.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Double avgPrice;
    @Column
    private Integer count;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
