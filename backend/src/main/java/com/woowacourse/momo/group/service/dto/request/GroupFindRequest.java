package com.woowacourse.momo.group.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.woowacourse.momo.group.repository.FindCondition;

@Getter
@Setter
@NoArgsConstructor
public class GroupFindRequest {

    private int page = 0;

    private Long category;

    private String keyword;

    private boolean excludeFinished;

    private boolean orderByDeadline;

    public FindCondition toFindCondition() {
        return new FindCondition(category, keyword, excludeFinished, orderByDeadline);
    }
}
