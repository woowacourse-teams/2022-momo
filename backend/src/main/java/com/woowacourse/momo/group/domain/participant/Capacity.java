package com.woowacourse.momo.group.domain.participant;

import java.util.Objects;

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

    public boolean isSame(int numberOfPeople) {
        return value == numberOfPeople;
    }

    public boolean isUnder(int numberOfPeople) {
        return value < numberOfPeople;
    }

    private void validateCapacityIsInRange(int value) {
        CapacityRange.validateCapacityIsInRange(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Capacity capacity = (Capacity) o;
        return value == capacity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Capacity{" + value + '}';
    }
}
