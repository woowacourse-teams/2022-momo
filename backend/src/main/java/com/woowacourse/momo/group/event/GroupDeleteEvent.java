package com.woowacourse.momo.group.event;

import lombok.Getter;

import com.woowacourse.momo.group.domain.Group;

@Getter
public class GroupDeleteEvent {

    private final Group group;
    private final Long id;

    public GroupDeleteEvent(Group group, Long id) {
        this.group = group;
        this.id = id;
    }
}
