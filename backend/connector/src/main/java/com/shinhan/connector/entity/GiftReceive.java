package com.shinhan.connector.entity;

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
public class GiftReceive {
    @Id
    @GeneratedValue
    @Column(name = "gift_receive_no")
    private Integer no;
    private String name;
    private String category;
    @Column(name = "price_min")
    private Long priceMin;
    @Column(name = "price_max")
    private Long priceMax;
    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_schedule_no")
    MySchedule mySchedule;
}
