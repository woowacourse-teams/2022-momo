package com.woowacourse.momo.group.domain.search.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.category.domain.Category;

@Getter
@AllArgsConstructor
public class GroupSummaryRepositoryResponse {

    private Long groupId;
    private String groupName;
    private Long hostId;
    private String hostName;
    private Category category;
    private int capacity;
    private int numOfParticipant;
    private boolean isClosedEarly;
    private LocalDateTime deadline;
}
