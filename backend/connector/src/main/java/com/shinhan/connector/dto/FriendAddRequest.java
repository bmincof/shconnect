package com.shinhan.connector.dto;

import com.shinhan.connector.entity.Friend;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.enums.Relation;
import lombok.Data;

@Data
public class FriendAddRequest {
    String name;
    String contact;
    String relation;
    String belong;
    String bankCode;
    String accountNumber;
    String image;

    public Friend toEntity(Member member) {
        return Friend.builder()
                .name(name)
                .contact(contact)
                .belong(belong)
                .bankCode(bankCode)
                .accountNumber(accountNumber)
                .image(image)
                .relation(Relation.getRelation(relation))
                .member(member)
                .build();
    }
}
