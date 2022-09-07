package com.woowacourse.momo.group.domain;

import static com.woowacourse.momo.group.exception.GroupErrorCode.NAME_CANNOT_BE_BLANK;

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

    @Column(name = "name", nullable = false)
    private String value;

    public GroupName(String value) {
        validateNameIsNotBlank(value);
        this.value = value;
    }

    private void validateNameIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new GroupException(NAME_CANNOT_BE_BLANK);
        }
    }
}
