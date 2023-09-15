package com.shinhan.connector.dto.request;

import lombok.Data;

@Data
public class FriendUpdateRequest {
    private String name;
    private String contact;
    private String relation;
    private String belong;
    private String bankCode;
    private String accountNumber;
    private String image;
}
