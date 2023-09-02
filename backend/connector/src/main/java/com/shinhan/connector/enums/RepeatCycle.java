package com.shinhan.connector.enums;

public enum RepeatCycle {
    NONE(0), WEEK(1), MONTH(2), YEAR(3);

    private int value;

    RepeatCycle(int value) {
        this.value = value;
    }

    public static RepeatCycle getRepeatCycle(int value) {
        switch (value) {
            case 1:
                return WEEK;
            case 2:
                return MONTH;
            case 3:
                return YEAR;
            default:
                return NONE;
        }
    }
}
