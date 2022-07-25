package com.woowacourse.momo.group.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.woowacourse.momo.group.domain.duration.Duration;

@SuppressWarnings("NonAsciiCharacters")
public class GroupFixture {

    private static final int YEAR = 2022;

    public static final LocalDateTime _6월_30일_23시_59분 = LocalDateTime.of(YEAR, 6, 30, 23, 59);
    public static final LocalDate _7월_1일 = LocalDate.of(YEAR, 7, 1);
    public static final LocalDate _7월_2일 = LocalDate.of(YEAR, 7, 2);
    public static final LocalTime _10시_00분 = LocalTime.of(10, 0);
    public static final LocalTime _12시_00분 = LocalTime.of(12, 0);

    public static final Duration _7월_1일부터_1일까지 = new Duration(_7월_1일, _7월_1일);
    public static final Duration _7월_1일부터_2일까지 = new Duration(_7월_1일, _7월_2일);
}
