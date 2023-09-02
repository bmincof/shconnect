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
public class GiftSend {
    @Id @GeneratedValue
    @Column(name = "gift_send_no")
    private Integer no;
    @Column(length = 100) @NotNull
    private String name;
    @Column(length = 20) @NotNull
    private String category;

    @NotNull
    private Long price;
    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_no")
    Schedule schedule;
}
