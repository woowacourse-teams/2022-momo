package com.woowacourse.momo.member.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserName {

    private static final int MINIMUM_LENGTH = 1;
    private static final int MAXIMUM_LENGTH = 20;

    @Column(name = "name", length = 36)
    private String value;

    private UserName(String value) {
        this.value = value;
    }

    public static UserName from(String value) {
        validateNameLengthIsValid(value);
        validateNameIsNotBlank(value);
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
            throw new MemberException(MemberErrorCode.MEMBER_NAME_SHOULD_NOT_BE_BLANK);
        }
    }

    private static void validateNameLengthIsValid(String value) {
        int length = value.length();
        if (length < MINIMUM_LENGTH || MAXIMUM_LENGTH < length) {
            throw new MemberException(MemberErrorCode.MEMBER_NAME_CANNOT_BE_OUT_OF_RANGE);
        }
    }

    public String getValue() {
        return Optional.ofNullable(value)
                .orElse("");
    }
}
