package com.shinhan.connector.enums;

public enum Alarm {
    NONE(0), ONE(1), WEEK(2), MONTH(3);

    private int value;

    Alarm(int value) {
        this.value = value;
    }

    public static Alarm getAlarm(int value) {
        switch (value) {
            case 1:
                return ONE;
            case 2:
                return WEEK;
            case 3:
                return MONTH;
            default:
                return NONE;
        }
    }
}
