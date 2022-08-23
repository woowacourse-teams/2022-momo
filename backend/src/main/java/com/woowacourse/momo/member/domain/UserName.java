package com.woowacourse.momo.member.domain;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.MEMBER_NAME_MUST_BE_VALID;
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
    private static final String GHOST_USER_NAME = "알 수 없음";

    @Column(name = "name", nullable = false, length = MAXIMUM)
    private String value;

    public UserName(String value) {
        validateNameIsNotBlank(value);
        validateNameLengthIsValid(value);
        this.value = value;
    }

    public UserName update(String value) {
        return new UserName(value);
    }

    public UserName delete() {
        return new UserName(GHOST_USER_NAME);
    }

    private void validateNameIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new MomoException(MEMBER_NAME_SHOULD_NOT_BE_BLANK);
        }
    }

    private void validateNameLengthIsValid(String value) {
        if (value.length() > MAXIMUM) {
            throw new MomoException(MEMBER_NAME_MUST_BE_VALID);
        }
    }
}
