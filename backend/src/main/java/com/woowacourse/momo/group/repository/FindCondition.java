package com.woowacourse.momo.group.repository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindCondition {

    private Long category;
    private String keyword;
    private boolean excludeFinished;
    private boolean orderByDeadline;

    public Long getCategory() {
        return category;
    }

    public String getKeyword() {
        return keyword;
    }

    public boolean excludeFinished() {
        return excludeFinished;
    }

    public boolean orderByDeadline() {
        return orderByDeadline;
    }
}
