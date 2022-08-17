package com.woowacourse.momo.group.domain.group;

public enum GroupCapacityRange {

    MINIMUM(1),
    MAXIMUM(99),
    ;

    private final int number;

    GroupCapacityRange(int number) {
        this.number = number;
    }

    public static boolean isOutOfRange(int capacity) {
        return (MINIMUM.number > capacity) || (capacity > MAXIMUM.number);
    }
}
