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
public class TributeLog {
    @Id @GeneratedValue
    @Column(name = "tribute_log_no")
    private Integer no;
    @Column(name = "age_range")
    private Integer ageRange;
    @Column
    private String category;
    @Column(name = "avg_price")
    private Double avgPrice;
    @Column
    private Integer count;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
