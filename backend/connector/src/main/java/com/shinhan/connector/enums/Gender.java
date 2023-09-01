package com.shinhan.connector.enums;

import lombok.Getter;

@Getter
public enum Gender {
    F("여성"),
    M("남성");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender getGender(String value) {
        if (value.contains("여성"))
            return F;
        else
            return M;
    }
}
