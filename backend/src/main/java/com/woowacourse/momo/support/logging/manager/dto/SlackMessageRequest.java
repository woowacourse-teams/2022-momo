package com.woowacourse.momo.support.logging.manager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SlackMessageRequest {

    private final String channel;
    private final String text;
}
