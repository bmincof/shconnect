package com.shinhan.connector.entity;

import com.sun.istack.NotNull;
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
    @Id @GeneratedValue
    @Column(name = "gift_receive_no")
    private Integer no;
    @Column(length = 100) @NotNull
    private String name;
    @Column(length = 20) @NotNull
    private String category;
    @Column(name = "price_min") @NotNull
    private Long priceMin;
    @Column(name = "price_max") @NotNull
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
