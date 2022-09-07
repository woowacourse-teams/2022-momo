package com.woowacourse.momo.group.domain.participant;

import static com.woowacourse.momo.group.exception.GroupErrorCode.CAPACITY_CANNOT_BE_OUT_OF_RANGE;

import com.woowacourse.momo.group.exception.GroupException;

public enum CapacityRange {

    MINIMUM(1),
    MAXIMUM(99),
    ;

    private final int value;

    CapacityRange(int value) {
        this.value = value;
    }

    public static void validateCapacityIsInRange(int capacity) {
        if (isOutOfRange(capacity)) {
            throw new GroupException(CAPACITY_CANNOT_BE_OUT_OF_RANGE);
        }
    }

    private static boolean isOutOfRange(int capacity) {
        return (MINIMUM.value > capacity) || (capacity > MAXIMUM.value);
    }
}
