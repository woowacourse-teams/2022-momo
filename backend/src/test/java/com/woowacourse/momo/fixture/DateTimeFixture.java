package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.DateFixture._1일_전;
import static com.woowacourse.momo.fixture.DateFixture._1일_후;
import static com.woowacourse.momo.fixture.TimeFixture._23시_59분;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DateTimeFixture {

    _1일_후_23시_59분(_1일_후, _23시_59분),
    _1일_전_23시_59분(_1일_전, _23시_59분);

    private final DateFixture date;
    private final TimeFixture time;

    private LocalDateTime instance;

    public LocalDateTime getInstance() {
        if (instance == null) {
            instance = LocalDateTime.of(date.getInstance(), time.getInstance());
        }
        return instance;
    }
}
