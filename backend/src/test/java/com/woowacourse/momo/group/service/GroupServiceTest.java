package com.woowacourse.momo.group.service;

import com.woowacourse.momo.group.exception.InvalidCategoryException;
import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Sql("classpath:init.sql")
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @DisplayName("모임을 생성한다.")
    @Test
    void create() {
        LocalDate startDate = LocalDate.parse("2022-07-08", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse("2022-07-08", DateTimeFormatter.ISO_LOCAL_DATE);
        DurationRequest duration = new DurationRequest(startDate, endDate);
        List<ScheduleRequest> schedules = List.of(new ScheduleRequest("월", LocalTime.of(11, 0), LocalTime.of(14, 0)));
        GroupRequest request = new GroupRequest("모모의 스터디", 1L, 1L, false, duration, schedules, LocalDateTime.now(), "", "");

        assertDoesNotThrow(() -> groupService.create(request));
    }

    @DisplayName("유효하지 않은 카테고리로 모임을 생성하면 예외가 발생한다.")
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

    @DisplayName("모임을 조회한다.")
    @Test
    void findById() {
        Long groupId = 1L;
        GroupResponse groupResponse = groupService.findById(groupId);

        assertThat(groupResponse).isNotNull();
    }

    @DisplayName("존재하지 않는 모임을 조회시 예외가 발생한다.")
    @Test
    void findByIdWithNotExistGroupId() {
        Long groupId = 100L;
        assertThatThrownBy(() -> groupService.findById(groupId)).
                isInstanceOf(NotFoundGroupException.class);
    }

    @DisplayName("모드 모임을 조회한다.")
    @Test
    void findAll() {
        List<GroupResponse> groups = groupService.findAll();

        assertThat(groups).hasSize(2);
    }

    @DisplayName("식별자를 통해 모임을 삭제한다.")
    @Test
    void delete() {
        Long groupId = 1L;

        groupService.delete(groupId);

        assertThatThrownBy(() -> groupService.findById(groupId))
                .isInstanceOf(NotFoundGroupException.class);
    }
}
