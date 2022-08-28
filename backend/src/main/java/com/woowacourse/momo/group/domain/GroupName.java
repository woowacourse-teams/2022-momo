package com.woowacourse.momo.group.domain;

import java.util.Objects;

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
public class GroupName {

    @Column(name = "name", nullable = false)
    private String value;

    public GroupName(String value) {
        validateNameIsNotBlank(value);
        this.value = value;
    }

    private void validateNameIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new MomoException(ErrorCode.GROUP_NAME_SHOULD_NOT_BE_BLANK);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupName groupName = (GroupName) o;
        return Objects.equals(value, groupName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
