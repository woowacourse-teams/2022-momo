package com.woowacourse.momo.fixture;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("NonAsciiCharacters")
@RequiredArgsConstructor
public enum DateFixture {

    _1일_전(LocalDate.now().minusDays(1)),
    _1일_후(LocalDate.now().plusDays(1)),
    _3일_후(LocalDate.now().plusDays(3)),
    _7일_후(LocalDate.now().plusDays(7));

    private final LocalDate instance;

    public LocalDate getInstance() {
        return instance;
    }
}
