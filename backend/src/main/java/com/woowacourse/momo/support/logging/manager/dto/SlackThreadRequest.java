package com.woowacourse.momo.support.logging.manager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SlackThreadRequest {

    private final String channel;
    private final String thread_ts;
    private final String text;
}
