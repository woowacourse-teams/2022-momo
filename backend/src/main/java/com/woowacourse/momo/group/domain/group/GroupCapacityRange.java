package com.woowacourse.momo.group.domain.group;

public enum GroupCapacityRange {

    MIN_CAPACITY(1),
    MAX_CAPACITY(99);

    private final int number;

    GroupCapacityRange(int number) {
        this.number = number;
    }

    public static boolean isOutOfRange(int capacity) {
        return (MIN_CAPACITY.number > capacity) || (capacity > MAX_CAPACITY.number);
    }
}
