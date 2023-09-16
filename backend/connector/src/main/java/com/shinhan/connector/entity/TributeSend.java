package com.shinhan.connector.entity;

import com.shinhan.connector.dto.request.TributeModifyRequest;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TributeSend {
    @Id
    @GeneratedValue
    @Column(name = "tribute_send_no")
    private Integer no;
    @Column(nullable = false)
    private Long amount;
    @Column(columnDefinition = "text")
    private String note;
    @ColumnDefault("false")
    private Boolean sent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_no")
    Schedule schedule;

    public void update(TributeModifyRequest tributeModifyRequest) {
        this.amount = tributeModifyRequest.getAmount() == null ? amount : tributeModifyRequest.getAmount();
        this.note = tributeModifyRequest.getNote() == null ? note : tributeModifyRequest.getNote();
    }

    public void send(Long amount) {
        this.sent = true;
        this.amount = amount;
    }
}
