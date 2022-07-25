package com.woowacourse.momo.fixture;

import java.time.LocalTime;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum TimeFixture {

    _10시_00분(10, 0),
    _12시_00분(12, 0),
    _23시_59분(23, 59);

    private final int hour;
    private final int minute;

    private LocalTime instance;

    public LocalTime getInstance() {
        if (Objects.isNull(instance)) {
            instance = LocalTime.of(hour, minute);
        }
        return instance;
    }
}
