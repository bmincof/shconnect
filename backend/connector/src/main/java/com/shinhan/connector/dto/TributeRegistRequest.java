package com.shinhan.connector.dto;

import com.shinhan.connector.entity.TributeReceive;
import com.shinhan.connector.entity.TributeSend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TributeRegistRequest {
    Integer scheduleNo;
    Integer friendNo;
    Long amount;
    String note;

}
