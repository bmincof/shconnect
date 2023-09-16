package com.shinhan.connector.enums;

public enum Relation {
    FAMILY("가족"), FRIEND("친구"), COWORKER("직장동료"), BUSINESS("거래처"), ETC("기타");

    String value;
    Relation(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Relation getRelation(String relation) {
        switch (relation) {
            case "친구":
                return FRIEND;
            case "가족":
                return FAMILY;
            case "직장동료":
                return COWORKER;
            case "거래처":
                return BUSINESS;
            default:
                return ETC;
        }
    }
}
