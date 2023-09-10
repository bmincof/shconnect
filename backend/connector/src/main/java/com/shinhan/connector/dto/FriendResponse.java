package com.shinhan.connector.dto;

import com.shinhan.connector.entity.Friend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendResponse {
    Integer friendNo;
    String name;
    String contact;
    String relation;
    String belong;
    String bankCode;
    String accountNumber;
    String image;

    public static FriendResponse fromEntity(Friend friend) {
        return FriendResponse.builder()
                .friendNo(friend.getNo())
                .name(friend.getName())
                .contact(friend.getContact())
                .relation(friend.getRelation().getValue())
                .belong(friend.getBelong())
                .bankCode(friend.getBankCode())
                .accountNumber(friend.getAccountNumber())
                .image(friend.getImage())
                .build();
    }
}
