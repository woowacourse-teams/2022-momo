package com.woowacourse.momo.group.service.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupPageResponse {

    boolean hasNextPage;
    int pageNumber;
    List<GroupSummaryResponse> groups;
}
