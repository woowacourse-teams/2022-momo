package com.woowacourse.momo.group.repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindCondition {

    private Long category;

    private String keyword;

    @Getter(AccessLevel.NONE)
    private boolean excludeFinished;

    @Getter(AccessLevel.NONE)
    private boolean orderByDeadline;

    public boolean excludeFinished() {
        return excludeFinished;
    }

    public boolean orderByDeadline() {
        return orderByDeadline;
    }
}
