package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.DateFixture._7월_1일;
import static com.woowacourse.momo.fixture.DateFixture._7월_2일;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DurationFixture {

    _7월_1일부터_1일까지(_7월_1일, _7월_1일),
    _7월_1일부터_2일까지(_7월_1일, _7월_2일),
    ;

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
