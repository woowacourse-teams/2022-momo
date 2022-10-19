package com.woowacourse.momo.global.logging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SlackMessage {
    private final String channel;
    private final String text;
}
