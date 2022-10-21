package com.woowacourse.momo.group.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.group.domain.calendar.Calendar;

@Getter
@AllArgsConstructor
public class GroupIdRepositoryResponse {
    private Long groupId;
    private Calendar calendar;
}
