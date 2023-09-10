package com.shinhan.connector.dto;

import com.shinhan.connector.entity.TributeReceive;
import com.shinhan.connector.entity.TributeSend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TributeResponse {
    Integer tributeNo;
    Integer scheduleNo;
    Integer friendNo;
    Long amount;
    String note;
    Boolean sent;

    public static TributeResponse entityToDto(Object tribute) {
        if (tribute instanceof TributeReceive) {
            TributeReceive tributeReceive = (TributeReceive) tribute;

            return TributeResponse.builder()
                    .tributeNo(tributeReceive.getNo())
                    .scheduleNo(tributeReceive.getMySchedule().getNo())
                    .friendNo(tributeReceive.getFriend().getNo())
                    .amount(tributeReceive.getAmount())
                    .note(tributeReceive.getNote())
                    .build();
        } else {
            TributeSend tributeSend = (TributeSend) tribute;

            return TributeResponse.builder()
                    .tributeNo(tributeSend.getNo())
                    .scheduleNo(tributeSend.getSchedule().getNo())
                    .amount(tributeSend.getAmount())
                    .note(tributeSend.getNote())
                    .sent(tributeSend.getSent())
                    .build();
        }
    }
}
