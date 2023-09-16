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
public class GiftReceive {
    @Id @GeneratedValue
    @Column(name = "gift_receive_no")
    private Integer no;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String category;
    @Column(name = "price_min", nullable = false)
    private Long priceMin;
    @Column(name = "price_max", nullable = false)
    private Long priceMax;
    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_schedule_no")
    MySchedule mySchedule;

    // 비즈니스 로직
    public GiftReceive isAllowed(int userId) {
        if (userId != mySchedule.getMember().getNo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신의 선물만 조회할 수 있습니다.");
        }
        return this;
    }

    public void update(GiftUpdateRequest updateRequest) {
        this.name = updateRequest.getName();
        this.category = updateRequest.getCategory();
        this.priceMin = updateRequest.getPriceMin();
        this.priceMax = updateRequest.getPriceMax();
        this.note = updateRequest.getNote();
    }
}
