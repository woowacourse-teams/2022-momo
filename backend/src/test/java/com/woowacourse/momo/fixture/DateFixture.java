package com.woowacourse.momo.fixture;

import java.time.LocalDate;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DateFixture {

    _6월_30일(2022, 6, 30),
    _7월_1일(2022, 7, 1),
    _7월_2일(2022, 7, 2);

    private final int year;
    private final int month;
    private final int day;

    private LocalDate instance;

    public LocalDate getInstance() {
        if (Objects.isNull(instance)) {
            instance = LocalDate.of(year, month, day);
        }
        return instance;
    }
}
