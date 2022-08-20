package com.woowacourse.momo.member.domain;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.MEMBER_NAME_MUST_BE_30_OR_LESS;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.MEMBER_NAME_SHOULD_NOT_BE_BLANK;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserName {

    private static final int MAXIMUM = 30;

    @Column(name = "name", nullable = false, length = 30)
    private String value;

    public UserName(String value) {
        this.value = value;
        validateNameIsNotBlank();
        validateNameLengthIsLessThan30();
    }

    public void update(String value) {
        this.value = value;
    }

    private void validateNameIsNotBlank() {
        if (value.isBlank()) {
            throw new MomoException(MEMBER_NAME_SHOULD_NOT_BE_BLANK);
        }
    }

    private void validateNameLengthIsLessThan30() {
        if (value.length() > MAXIMUM) {
            throw new MomoException(MEMBER_NAME_MUST_BE_30_OR_LESS);
        }
    }
}
