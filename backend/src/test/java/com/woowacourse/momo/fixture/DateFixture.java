package com.woowacourse.momo.fixture;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DateFixture {

    어제(LocalDate.now().minusDays(1)),
    내일(LocalDate.now().plusDays(1)),
    이틀후(LocalDate.now().plusDays(2)),
    일주일후(LocalDate.now().plusDays(7));

    private final LocalDate instance;

    public LocalDate getInstance() {
        return instance;
    }
}
