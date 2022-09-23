package com.woowacourse.momo.group.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.woowacourse.momo.group.domain.search.SearchCondition;

@Getter
@Setter
@NoArgsConstructor
public class GroupSearchRequest {

    private int page = 0;

    private Long category;

    private String keyword;

    private boolean excludeFinished;

    private boolean orderByDeadline;

    public SearchCondition toFindCondition() {
        return new SearchCondition(category, keyword, excludeFinished, orderByDeadline);
    }
}
