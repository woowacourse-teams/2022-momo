package com.woowacourse.momo.fixture.calendar;

import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.일주일후;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._12시_00분;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.momo.fixture.calendar.datetime.DateFixture;
import com.woowacourse.momo.fixture.calendar.datetime.TimeFixture;
import com.woowacourse.momo.group.controller.param.calendar.ScheduleParam;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.service.request.calendar.ScheduleRequest;
import com.woowacourse.momo.group.service.request.calendar.SchedulesRequest;

@SuppressWarnings("NonAsciiCharacters")
public enum ScheduleFixture {

    내일_10시부터_12시까지(내일, _10시_00분, _12시_00분),
    이틀후_10시부터_12시까지(이틀후, _10시_00분, _12시_00분),
    일주일후_10시부터_12시까지(일주일후, _10시_00분, _12시_00분),
    ;

    private final DateFixture date;
    private final TimeFixture startTime;
    private final TimeFixture endTime;

    ScheduleFixture(DateFixture date, TimeFixture startTime, TimeFixture endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule toSchedule() {
        return new Schedule(date.toDate(), startTime.toTime(), endTime.toTime());
    }

    public static Schedules toSchedules(ScheduleFixture... schedules) {
        return toSchedules(List.of(schedules));
    }

    public static Schedules toSchedules(List<ScheduleFixture> schedules) {
        return new Schedules(schedules.stream()
                .map(ScheduleFixture::toSchedule)
                .collect(Collectors.toUnmodifiableList()));
    }

    public static Schedules emptySchedules() {
        return new Schedules(Collections.emptyList());
    }

    public static SchedulesRequest toRequests(List<ScheduleFixture> schedules) {
        return new SchedulesRequest(schedules.stream()
                .map(ScheduleFixture::toRequest)
                .collect(Collectors.toUnmodifiableList()));
    }

    public static SchedulesRequest toRequests(ScheduleFixture... schedules) {
        return toRequests(List.of(schedules));
    }

    private ScheduleRequest toRequest() {
        return new ScheduleRequest(date.toDate(), startTime.toTime(), endTime.toTime());
    }

    public static List<ScheduleParam> toParams(List<ScheduleFixture> schedules) {
        return schedules.stream()
                .map(ScheduleFixture::toParam)
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<ScheduleParam> toParams(ScheduleFixture... schedules) {
        return toParams(List.of(schedules));
    }

    private ScheduleParam toParam() {
        return new ScheduleParam(date.toDate(), startTime.toTime(), endTime.toTime());
    }

    public LocalDate getDate() {
        return date.toDate();
    }

    public LocalTime getStartTime() {
        return startTime.toTime();
    }

    public LocalTime getEndTime() {
        return endTime.toTime();
    }
}
