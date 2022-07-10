package com.woowacourse.momo.group.service;

import com.woowacourse.momo.group.exception.InvalidCategoryException;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Sql("classpath:init.sql")
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @DisplayName("그룹을 생성한다.")
    @Test
    void create() {
        LocalDate startDate = LocalDate.parse("2022-07-08", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse("2022-07-08", DateTimeFormatter.ISO_LOCAL_DATE);
        DurationRequest duration = new DurationRequest(startDate, endDate);
        List<ScheduleRequest> schedules = List.of(new ScheduleRequest("월", LocalTime.of(11, 0), LocalTime.of(14, 0)));
        GroupRequest request = new GroupRequest("모모의 스터디", 1L, 1L, false, duration, schedules, LocalDateTime.now(), "", "");

        assertDoesNotThrow(() -> groupService.create(request));
    }

    @DisplayName("유효하지 않은 카테고리로 그룹을 생성하면 예외가 발생한다.")
    @Test
    void createWithInvalidCategoryId() {
        Long categoryId = 100L;
        LocalDate startDate = LocalDate.parse("2022-07-08", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse("2022-07-08", DateTimeFormatter.ISO_LOCAL_DATE);
        DurationRequest duration = new DurationRequest(startDate, endDate);
        List<ScheduleRequest> schedules = List.of(new ScheduleRequest("월", LocalTime.of(11, 0), LocalTime.of(14, 0)));
        GroupRequest request = new GroupRequest("모모의 스터디", 1L, categoryId, false, duration, schedules, LocalDateTime.now(), "", "");

        assertThatThrownBy(() -> groupService.create(request))
                .isInstanceOf(InvalidCategoryException.class);
    }
}
