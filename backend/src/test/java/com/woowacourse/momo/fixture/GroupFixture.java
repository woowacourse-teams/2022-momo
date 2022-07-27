package com.woowacourse.momo.fixture;

import static com.woowacourse.momo.fixture.DateTimeFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture._7월_1일부터_1일까지;
import static com.woowacourse.momo.fixture.DurationFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import com.woowacourse.momo.acceptance.group.GroupRestHandler;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;

@SuppressWarnings("NonAsciiCharacters")
@Getter
public enum GroupFixture {

    MOMO_STUDY("모모의 스터디", Category.STUDY, _7월_1일부터_2일까지, List.of(_7월_1일_10시부터_12시까지),
            _6월_30일_23시_59분, "루터회관 13층", "같이 공부해요!!"),
    MOMO_TRAVEL("선릉 산책", Category.TRAVEL, _7월_1일부터_1일까지, List.of(_7월_1일_10시부터_12시까지),
            _6월_30일_23시_59분, "선릉", "점심 먹고 선릉 나들이~!!"),
    DUDU_STUDY("두두와의 스터디", Category.STUDY, _7월_1일부터_2일까지, List.of(_7월_1일_10시부터_12시까지),
            _6월_30일_23시_59분, "루터회관 13층", "두두랑 함께 공부해요!!");

    private final String name;
    private final Long categoryId;
    private final Duration duration;
    private final List<Schedule> schedules;
    private final LocalDateTime deadline;
    private final String location;
    private final String description;

    GroupFixture(String name, Category category, DurationFixture duration, List<ScheduleFixture> schedules,
                 DateTimeFixture deadline, String location, String description) {
        this.name = name;
        this.categoryId = category.getId();
        this.duration = duration.getInstance();
        this.schedules = schedules.stream()
                .map(ScheduleFixture::newInstance)
                .collect(Collectors.toList());
        this.deadline = deadline.getInstance();
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
