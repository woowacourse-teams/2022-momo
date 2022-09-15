package com.woowacourse.momo.group.repository;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindCondition {

    private Long category;

    private String keyword;

    private boolean excludeFinished;

    private boolean orderByDeadline;

    public Optional<Long> getCategory() {
        return Optional.ofNullable(category);
    }

    public Optional<String> getKeyword() {
        return Optional.ofNullable(keyword);
    }

    public boolean excludeFinished() {
        return excludeFinished;
    }

    public boolean orderByDeadline() {
        return orderByDeadline;
    }
}
