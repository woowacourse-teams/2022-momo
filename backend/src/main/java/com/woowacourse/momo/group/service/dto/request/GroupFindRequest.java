package com.woowacourse.momo.group.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupFindRequest {

    private int page = 0;

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