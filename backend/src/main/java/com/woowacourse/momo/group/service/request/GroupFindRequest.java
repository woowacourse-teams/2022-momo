package com.woowacourse.momo.group.service.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupFindRequest {

    private int page = 0;
    private Long category;
    private String keyword;
    private boolean excludeFinished;
    private boolean orderByDeadline;


    public boolean excludeFinished() {
        return excludeFinished;
    }

    public boolean orderByDeadline() {
        return orderByDeadline;
    }
}
