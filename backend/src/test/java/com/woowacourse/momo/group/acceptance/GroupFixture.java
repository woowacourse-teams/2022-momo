package com.woowacourse.momo.group.acceptance;

import static com.woowacourse.momo.group.fixture.GroupFixture._10시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._12시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일부터_1일까지;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일부터_2일까지;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;

@SuppressWarnings("NonAsciiCharacters")
@Getter
public enum GroupFixture {

    MOMO_STUDY("모모의 스터디", Category.STUDY, _7월_1일부터_2일까지, List.of(new Schedule(_7월_1일, _10시_00분, _12시_00분)),
            _6월_30일_23시_59분, "루터회관 13층", "같이 공부해요!!"),
    MOMO_TRAVEL("선릉 산책", Category.TRAVEL, _7월_1일부터_1일까지, List.of(new Schedule(_7월_1일, _10시_00분, _12시_00분)),
            _6월_30일_23시_59분, "선릉", "점심 먹고 선릉 나들이~!!"),
    DUDU_STUDY("두두와의 스터디", Category.STUDY, _7월_1일부터_2일까지, List.of(new Schedule(_7월_1일, _10시_00분, _12시_00분)),
            _6월_30일_23시_59분, "루터회관 13층", "두두랑 함께 공부해요!!");

    private final String name;
    private final Long categoryId;
    private final Duration duration;
    private final List<Schedule> schedules;
    private final LocalDateTime deadline;
    private final String location;
    private final String description;

    GroupFixture(String name, Category category, Duration duration, List<Schedule> schedules, LocalDateTime deadline,
                 String location, String description) {
        this.name = name;
        this.categoryId = category.getId();
        this.duration = duration;
        this.schedules = schedules;
        this.deadline = deadline;
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
