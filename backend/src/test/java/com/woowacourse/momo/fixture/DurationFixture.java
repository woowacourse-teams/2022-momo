package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.DateFixture._3일_후;
import static com.woowacourse.momo.fixture.DateFixture._7일_후;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.duration.Duration;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DurationFixture {

    _3일_후부터_3일_후까지(_3일_후, _3일_후),
    _3일_후부터_7일_후까지(_3일_후, _7일_후),
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
