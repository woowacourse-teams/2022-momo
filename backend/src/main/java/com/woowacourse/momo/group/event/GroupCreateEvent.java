package com.woowacourse.momo.group.event;

import lombok.Getter;

import com.woowacourse.momo.category.domain.Category;

@Getter
public class GroupCreateEvent {

    private final Long groupId;
    private final Category category;

    public GroupCreateEvent(Long groupId, Category category) {
        this.groupId = groupId;
        this.category = category;
    }
}
