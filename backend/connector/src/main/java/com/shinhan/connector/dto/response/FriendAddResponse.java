package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.Friend;
import lombok.Data;

@Data
public class FriendAddResponse {
    Integer friendNo;
    String contact;
    String relation;
    String belong;
    String bankCode;
    String accountNumber;
    String image;

    public FriendAddResponse(Friend friend) {
        this.friendNo = friend.getNo();
        this.contact = friend.getContact();
        this.bankCode = friend.getBankCode();
        this.accountNumber = friend.getAccountNumber();
        this.relation = friend.getRelation().getValue();
        this.belong = friend.getBelong();
        this.image = friend.getImage();
    }
}
