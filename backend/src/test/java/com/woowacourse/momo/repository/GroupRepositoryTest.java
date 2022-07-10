package com.woowacourse.momo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.domain.group.Duration;
import com.woowacourse.momo.domain.group.Group;
import com.woowacourse.momo.domain.group.Schedule;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        Duration duration = Duration.of("2022-07-08", "2022-07-08");
        Group group = new Group("momo 회의", 1L, 1L, false, duration, LocalDateTime.now(),
                List.of(Schedule.of("월", "11:00:00", "11:00:00")), "", "");

        Group actual = groupRepository.save(group);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(group);
    }

    @DisplayName("스케쥴이 지정되지 않은 모임을 저장한다")
    @Test
    void saveGroupWithoutSchedules() {
        Duration duration = Duration.of("2022-07-08", "2022-07-08");
        Group group = new Group("momo 회의", 1L, 1L, false, duration, LocalDateTime.now(),
                Collections.emptyList(), "", "");

        Group actual = groupRepository.save(group);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(group);
    }

    @DisplayName("식별자를 통해 스케줄을 조회한다")
    @Test
    void findById() {
        Duration duration = Duration.of("2022-07-08", "2022-07-08");
        Group group = new Group("momo 회의", 1L, 1L, false, duration, LocalDateTime.now(),
                List.of(Schedule.of("월", "11:00:00", "11:00:00")), "", "");

        Group expected = groupRepository.save(group);
        Optional<Group> actual = groupRepository.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("모임 리스트를 조회한다")
    @Test
    void findAll() {
        Duration duration = Duration.of("2022-07-08", "2022-07-08");
        Group group1 = new Group("momo 회의", 1L, 1L, false, duration, LocalDateTime.now(),
                List.of(Schedule.of("월", "11:00:00", "11:00:00")), "", "");
        Group group2 = new Group("momo 회의", 2L, 1L, false, duration, LocalDateTime.now(),
                List.of(Schedule.of("월", "11:00:00", "11:00:00")), "", "");

        Group expected1 = groupRepository.save(group1);
        Group expected2 = groupRepository.save(group2);

        List<Group> actual = groupRepository.findAll();
        assertThat(actual).contains(expected1, expected2);
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void deleteById() {
        Duration duration = Duration.of("2022-07-08", "2022-07-08");
        Group group = new Group("momo 회의", 1L, 1L, false, duration, LocalDateTime.now(),
                List.of(Schedule.of("월", "11:00:00", "11:00:00")), "", "");
        Long groupId = groupRepository.save(group).getId();

        groupRepository.deleteById(groupId);
        groupRepository.flush();
        Optional<Group> actual = groupRepository.findById(groupId);

        assertThat(actual).isEmpty();
    }
}
