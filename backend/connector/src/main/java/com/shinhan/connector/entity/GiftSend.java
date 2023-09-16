package com.shinhan.connector.entity;

import com.shinhan.connector.dto.request.GiftUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String category;

    @Column(nullable = false)
    private Long price;
    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_no")
    Schedule schedule;

    // 비즈니스 로직
    public GiftSend isAllowed(int userId) {
        if (userId != schedule.getMember().getNo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신의 선물만 조회할 수 있습니다.");
        }
        return this;
    }

    public void update(GiftUpdateRequest updateRequest) {
        this.name = updateRequest.getName();
        this.category = updateRequest.getCategory();
        this.price = updateRequest.getPrice();
        this.note = updateRequest.getNote();
    }
}
