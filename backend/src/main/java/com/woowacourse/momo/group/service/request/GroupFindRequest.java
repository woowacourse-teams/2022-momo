package com.woowacourse.momo.group.service.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupFindRequest {

    private Integer page = 0;
    private Long category;
    private String keyword;
    private Boolean excludeFinished;
    private Boolean orderByDeadline;
}
