package com.woowacourse.momo.member.event;

import lombok.Getter;

@Getter
public class MemberDeleteEvent {

    private final Long id;

    public MemberDeleteEvent(Long id) {
        this.id = id;
    }
}
