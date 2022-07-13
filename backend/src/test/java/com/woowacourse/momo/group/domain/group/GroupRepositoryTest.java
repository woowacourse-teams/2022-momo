package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.duration.Duration;
import com.woowacourse.momo.group.domain.schedule.Schedule;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        List<Schedule> schedules = List.of(Schedule.of("2022-07-01", "10:00", "12:00"));
        Group group = constructGroup(schedules);

        Group savedGroup = groupRepository.save(group);

        assertAll(
                () -> assertThat(savedGroup.getId()).isNotNull(),
                () -> assertThat(savedGroup).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(group)
        );
    }

    @DisplayName("스케쥴이 지정되지 않은 모임을 저장한다")
    @Test
    void saveGroupWithoutSchedules() {
        List<Schedule> schedules = Collections.emptyList();
        Group group = constructGroup(schedules);

        Group savedGroup = groupRepository.save(group);

        assertAll(
                () -> assertThat(savedGroup.getId()).isNotNull(),
                () -> assertThat(savedGroup).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(group)
        );
    }

    @DisplayName("식별자를 통해 모임을 조회한다")
    @Test
    void findById() {
        List<Schedule> schedules = List.of(
                Schedule.of("2022-07-01", "10:00", "12:00"),
                Schedule.of("2022-07-03", "10:00", "12:00")
        );
        Group group = constructGroup(schedules);
        Group savedGroup = groupRepository.save(group);

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get()).usingRecursiveComparison()
                .isEqualTo(savedGroup);
    }

    @DisplayName("모임 리스트를 조회한다")
    @Test
    void findAll() {
        Group group1 = constructGroup(List.of(Schedule.of("2022-07-01", "10:00", "12:00")));
        Group group2 = constructGroup(List.of(Schedule.of("2022-07-01", "10:00", "12:00")));
        Group savedGroup1 = groupRepository.save(group1);
        Group savedGroup2 = groupRepository.save(group2);

        List<Group> actual = groupRepository.findAll();

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(savedGroup1, savedGroup2));
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void deleteById() {
        List<Schedule> schedules = List.of(Schedule.of("2022-07-01", "10:00", "12:00"));
        Group group = constructGroup(schedules);
        groupRepository.save(group);

        groupRepository.deleteById(group.getId());
        groupRepository.flush();
        Optional<Group> foundGroup = groupRepository.findById(group.getId());

        assertThat(foundGroup).isEmpty();
    }

    private Group constructGroup(List<Schedule> schedules) {
        Duration duration = Duration.of("2022-07-08", "2022-07-08");

        return new Group("momo 회의", 1L, Category.STUDY, duration, LocalDateTime.now(),
                schedules, "", "");
    }
}
