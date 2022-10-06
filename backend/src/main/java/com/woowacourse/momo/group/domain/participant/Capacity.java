package com.woowacourse.momo.group.domain.participant;

import static com.woowacourse.momo.group.exception.GroupErrorCode.CAPACITY_CANNOT_BE_OUT_OF_RANGE;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.group.exception.GroupException;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Capacity {

    private static final int MINIMUM = 1;
    private static final int MAXIMUM = 99;

    @Column(name = "capacity", nullable = false)
    private int value;

    public Capacity(int value) {
        validateCapacityIsInRange(value);
        this.value = value;
    }

    public boolean isEqualOrOver(int numberOfPeople) {
        return value <= numberOfPeople;
    }

    public boolean isSmallThan(int numberOfPeople) {
        return value < numberOfPeople;
    }

    private void validateCapacityIsInRange(int value) {
        if (isOutOfRange(value)) {
            throw new GroupException(CAPACITY_CANNOT_BE_OUT_OF_RANGE);
        }
    }

    private static boolean isOutOfRange(int capacity) {
        return MINIMUM > capacity || capacity > MAXIMUM;
    }
}
