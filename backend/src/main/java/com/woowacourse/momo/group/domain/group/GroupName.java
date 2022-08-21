package com.woowacourse.momo.group.domain.group;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_NAME_SHOULD_NOT_BE_BLANK;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class GroupName {

    @Column(name = "name", nullable = false)
    private String value;

    public GroupName(String value) {
        this.value = value;
        validateNameIsNotBlank();
    }

    private void validateNameIsNotBlank() {
        if (value.isBlank()) {
            throw new MomoException(GROUP_NAME_SHOULD_NOT_BE_BLANK);
        }
    }
}
