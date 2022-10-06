package com.woowacourse.momo.member.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class MemberDeleteEvent extends ApplicationEvent {

    private final Long id;

    public MemberDeleteEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }
}
