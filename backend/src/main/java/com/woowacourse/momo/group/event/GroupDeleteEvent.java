package com.woowacourse.momo.group.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class GroupDeleteEvent extends ApplicationEvent {

    private final Long id;

    public GroupDeleteEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }
}
