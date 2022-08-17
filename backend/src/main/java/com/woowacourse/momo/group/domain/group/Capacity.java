package com.woowacourse.momo.group.domain.group;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

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
        if (GroupCapacityRange.isOutOfRange(value)) {
            throw new MomoException(ErrorCode.GROUP_MEMBERS_NOT_IN_RANGE);
        }
    }
}
