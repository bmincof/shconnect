package com.shinhan.connector.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shinhan.connector.entity.Friend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TributeModifyRequest {
    Integer friendNo;
    Long amount;
    String note;

    @JsonIgnore
    Friend friend;
}
