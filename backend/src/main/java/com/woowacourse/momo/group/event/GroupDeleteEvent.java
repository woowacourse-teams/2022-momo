package com.woowacourse.momo.group.event;

import lombok.Getter;

@Getter
public class GroupDeleteEvent {

    private final Long id;

    public GroupDeleteEvent(Long id) {
        this.id = id;
    }
}
