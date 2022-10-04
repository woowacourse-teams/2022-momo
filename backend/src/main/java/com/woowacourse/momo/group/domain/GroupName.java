package com.woowacourse.momo.group.domain;

import static com.woowacourse.momo.group.exception.GroupErrorCode.NAME_CANNOT_BE_BLANK;
import static com.woowacourse.momo.group.exception.GroupErrorCode.NAME_CANNOT_BE_OUT_OF_RANGE;

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
public class GroupName {

    private static final int MINIMUM_LENGTH = 1;
    private static final int MAXIMUM_LENGTH = 50;

    @Column(name = "name", nullable = false, length = 50)
    private String value;

    public GroupName(String value) {
        validateNotBlank(value);
        validateLengthInRange(value);
        this.value = value;
    }

    private void validateNotBlank(String value) {
        if (value.isBlank()) {
            throw new GroupException(NAME_CANNOT_BE_BLANK);
        }
    }

    private void validateLengthInRange(String value) {
        if (isNameOutOfRange(value)) {
            throw new GroupException(NAME_CANNOT_BE_OUT_OF_RANGE);
        }
    }

    private boolean isNameOutOfRange(String value) {
        int length = value.length();
        return (length < MINIMUM_LENGTH) || (MAXIMUM_LENGTH < length);
    }
}
