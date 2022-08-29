package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import com.woowacourse.momo.acceptance.group.GroupRestHandler;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.calendar.DurationFixture;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;

@SuppressWarnings("NonAsciiCharacters")
@Getter
public enum GroupFixture {

    MOMO_STUDY("모모의 스터디", Category.STUDY, 12, 이틀후부터_일주일후까지, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분, "루터회관 13층", "같이 공부해요!!"),
    MOMO_TRAVEL("선릉 산책", Category.TRAVEL, 99, 이틀후_하루동안, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분, "선릉", "점심 먹고 선릉 나들이~!!"),
    DUDU_STUDY("두두와의 스터디", Category.STUDY, 8, 이틀후부터_일주일후까지, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분, "루터회관 13층", "두두랑 함께 공부해요!!"),
    DUDU_COFFEE_TIME("두두와의 커피타임", Category.CAFE, 2, 이틀후_하루동안, List.of(이틀후_10시부터_12시까지),
            내일_23시_59분, "잠실역 스타벅스", "두두가 쏘는 커피~ 선착순 1명!!");

    private final String name;
    private final Long categoryId;
    private final Integer capacity;
    private final Duration duration;
    private final List<Schedule> schedules;
    private final LocalDateTime deadline;
    private final String location;
    private final String description;

    GroupFixture(String name, Category category, Integer capacity, DurationFixture duration,
                 List<ScheduleFixture> schedules, DateTimeFixture deadline, String location, String description) {
        this.name = name;
        this.categoryId = category.getId();
        this.capacity = capacity;
        this.duration = duration.getDuration();
        this.schedules = schedules.stream()
                .map(ScheduleFixture::getSchedule)
                .collect(Collectors.toList());
        this.deadline = deadline.getDateTime();
        this.location = location;
        this.description = description;
    }

    public Long 을_생성한다(String accessToken) {
        return GroupRestHandler.모임을_생성한다(accessToken, this)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(GroupIdResponse.class)
                .getGroupId();
    }
}
