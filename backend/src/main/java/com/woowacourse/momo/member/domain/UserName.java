package com.woowacourse.momo.member.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserName {

    private static final int MAXIMUM = 30;

    @Column(name = "name", length = 36)
    private String value;

    public UserName(String value) {
        this.value = value;
    }

    public static UserName from(String value) {
        validateNameIsNotBlank(value);
        validateNameLengthIsValid(value);
        return new UserName(value);
    }

    public static UserName deleted() {
        return new UserName(null);
    }

    public UserName update(String value) {
        return UserName.from(value);
    }

    private static void validateNameIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new MomoException(GlobalErrorCode.MEMBER_NAME_SHOULD_NOT_BE_BLANK);
        }
    }

    private static void validateNameLengthIsValid(String value) {
        if (value.length() > MAXIMUM) {
            throw new MomoException(GlobalErrorCode.MEMBER_NAME_MUST_BE_VALID);
        }
    }

    public String getValue() {
        return Optional.ofNullable(value)
                .orElse("");
    }
}
