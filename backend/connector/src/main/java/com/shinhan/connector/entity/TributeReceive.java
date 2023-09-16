package com.shinhan.connector.entity;

import com.shinhan.connector.dto.request.TributeModifyRequest;
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
public class TributeReceive {
    @Id
    @GeneratedValue
    @Column(name = "tribute_receive_no")
    private Integer no;
    @Column(nullable = false)
    private Long amount;
    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_schedule_no")
    MySchedule mySchedule;

    public void update(TributeModifyRequest tributeModifyRequest) {
        this.amount = tributeModifyRequest.getAmount() == null ? amount : tributeModifyRequest.getAmount();
        this.note = tributeModifyRequest.getNote() == null ? note : tributeModifyRequest.getNote();
        this.friend = tributeModifyRequest.getFriend() == null ? friend : tributeModifyRequest.getFriend();
    }
}
