package com.woowacourse.momo.group.domain.group;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Capacity {

    @Column(name = "capacity", nullable = false)
    private int value;

    public Capacity(int value) {
        validateCapacityIsInRange(value);
        this.value = value;
    }

    public boolean isFull(int numberOfPeople) {
        return value <= numberOfPeople;
    }

    public boolean isUnder(int numberOfPeople) {
        return value < numberOfPeople;
    }

    private void validateCapacityIsInRange(int value) {
        GroupCapacityRange.validateCapacityIsInRange(value);
    }
}
