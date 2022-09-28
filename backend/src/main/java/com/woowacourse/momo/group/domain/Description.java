package com.woowacourse.momo.group.domain;

import static com.woowacourse.momo.group.exception.GroupErrorCode.DESCRIPTION_CANNOT_BE_OUT_OF_RANGE;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

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
public class Description {

    private static final int MAXIMUM_LENGTH = 1000;

    @Lob
    @Column(name = "description", nullable = false)
    private String value;

    public Description(String value) {
        validateLengthInRange(value);
        this.value = value;
    }

    private void validateLengthInRange(String value) {
        if (MAXIMUM_LENGTH < value.length()) {
            throw new GroupException(DESCRIPTION_CANNOT_BE_OUT_OF_RANGE);
        }
    }
}
