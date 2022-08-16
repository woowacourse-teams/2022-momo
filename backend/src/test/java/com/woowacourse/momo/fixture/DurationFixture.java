package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.DateFixture.일주일후;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DurationFixture {

    이틀후_하루동안(이틀후, 이틀후),
    이틀후부터_일주일후까지(이틀후, 일주일후),
    일주일후_하루동안(일주일후, 일주일후);

    private final DateFixture start;
    private final DateFixture end;

    private Duration instance;

    public Duration getInstance() {
        if (instance == null) {
            instance = new Duration(start.getInstance(), end.getInstance());
        }
        return instance;
    }
}
