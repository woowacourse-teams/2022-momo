package com.woowacourse.momo.group.event;

import lombok.Getter;

import com.woowacourse.momo.group.domain.Group;

@Getter
public class GroupCreateEvent {

    private final Group group;

    public GroupCreateEvent(Group group) {
        this.group = group;
    }
}
