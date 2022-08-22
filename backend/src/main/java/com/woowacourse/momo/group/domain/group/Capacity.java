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
        this.value = value;
        validateRange();
    }

    public boolean isFull(int numberOfPeople) {
        return value <= numberOfPeople;
    }

    private void validateRange() {
        GroupCapacityRange.validateCapacityIsInRange(value);
    }
}
