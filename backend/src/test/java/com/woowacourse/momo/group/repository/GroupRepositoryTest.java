package com.woowacourse.momo.group.repository;

import com.woowacourse.momo.group.domain.Duration;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        List<Schedule> schedules = List.of(new Schedule(LocalDate.of(2022, 7, 1),
                LocalTime.of(10, 0), LocalTime.of(12, 0)));
        Group group = constructGroup(schedules);

        Group savedGroup = groupRepository.save(group);
        List<Schedule> savedSchedules = scheduleRepository.findByGroupId(savedGroup.getId());

        assertThat(savedGroup.getId()).isNotNull();
        assertAll(
                () -> assertThat(savedGroup).usingRecursiveComparison()
                        .isEqualTo(group),
                () -> assertThat(savedSchedules).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(schedules)
        );
    }

    @DisplayName("스케쥴이 지정되지 않은 모임을 저장한다")
    @Test
    void saveGroupWithoutSchedules() {
        List<Schedule> schedules = Collections.emptyList();
        Group group = constructGroup(schedules);

        Group savedGroup = groupRepository.save(group);
        List<Schedule> savedSchedules = scheduleRepository.findByGroupId(savedGroup.getId());

        assertThat(savedGroup.getId()).isNotNull();
        assertAll(
                () -> assertThat(savedGroup).usingRecursiveComparison()
                        .isEqualTo(group),
                () -> assertThat(savedSchedules).isEmpty()
        );
    }

    @DisplayName("식별자를 통해 스케줄을 조회한다")
    @Test
    void findById() {
        List<Schedule> schedules = List.of(new Schedule(LocalDate.of(2022, 7, 1),
                        LocalTime.of(10, 0), LocalTime.of(12, 0)),
                new Schedule(LocalDate.of(2022, 7, 3),
                        LocalTime.of(10, 0), LocalTime.of(12, 0)));
        Group group = constructGroup(schedules);
        Group savedGroup = groupRepository.save(group);

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());
        List<Schedule> savedSchedules = scheduleRepository.findByGroupId(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertAll(
                () -> assertThat(foundGroup.get()).isEqualTo(savedGroup),
                () -> assertThat(savedSchedules).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(schedules)
        );
    }

    @DisplayName("모임 리스트를 조회한다")
    @Test
    void findAll() {
        List<Schedule> schedules = List.of(new Schedule(LocalDate.of(2022, 7, 1),
                LocalTime.of(10, 0), LocalTime.of(12, 0)));
        Group group1 = constructGroup(schedules);
        Group group2 = constructGroup(schedules);
        Group savedGroup1 = groupRepository.save(group1);
        Group savedGroup2 = groupRepository.save(group2);

        List<Group> actual = groupRepository.findAll();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(savedGroup1, savedGroup2));
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void deleteById() {
        List<Schedule> schedules = List.of(new Schedule(LocalDate.of(2022, 7, 1),
                LocalTime.of(10, 0), LocalTime.of(12, 0)));
        Group group = constructGroup(schedules);
        groupRepository.save(group);

        groupRepository.deleteById(group.getId());
        groupRepository.flush();
        Optional<Group> foundGroup = groupRepository.findById(group.getId());
        List<Schedule> foundSchedules = scheduleRepository.findByGroupId(group.getId());

        assertAll(
                () -> assertThat(foundGroup).isEmpty(),
                () -> assertThat(foundSchedules).isEmpty()
        );
    }

    private Group constructGroup(List<Schedule> schedules) {
        LocalDate startDate = LocalDate.of(2022, 7, 8);
        LocalDate endDate = LocalDate.of(2022, 7, 8);
        Duration duration = new Duration(startDate, endDate);

        return new Group("momo 회의", 1L, 1L, duration, LocalDateTime.now(),
                schedules, "", "");
    }
}
