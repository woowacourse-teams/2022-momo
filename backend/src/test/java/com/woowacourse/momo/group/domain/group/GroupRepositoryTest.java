package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.group.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;
import static com.woowacourse.momo.group.fixture.ScheduleFixture._7월_2일_10시부터_12시까지;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.schedule.Schedule;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        List<Schedule> schedules = List.of(_7월_1일_10시부터_12시까지.newInstance());
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
        List<Schedule> schedules = List.of(_7월_1일_10시부터_12시까지.newInstance(), _7월_2일_10시부터_12시까지.newInstance());
        Group group = constructGroup(schedules);
        Group savedGroup = groupRepository.save(group);

        synchronize();

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get()).usingRecursiveComparison()
                .isEqualTo(savedGroup);
    }

    @DisplayName("모임 리스트를 조회한다")
    @Test
    void findAll() {
        Group group1 = constructGroup(List.of(_7월_1일_10시부터_12시까지.newInstance()));
        Group group2 = constructGroup(List.of(_7월_2일_10시부터_12시까지.newInstance()));
        Group savedGroup1 = groupRepository.save(group1);
        Group savedGroup2 = groupRepository.save(group2);

        synchronize();

        List<Group> actual = groupRepository.findAll();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(savedGroup1, savedGroup2));
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void deleteById() {
        List<Schedule> schedules = List.of(_7월_1일_10시부터_12시까지.newInstance());
        Group group = constructGroup(schedules);
        groupRepository.save(group);

        synchronize();

        groupRepository.deleteById(group.getId());

        synchronize();

        Optional<Group> foundGroup = groupRepository.findById(group.getId());

        assertThat(foundGroup).isEmpty();
    }

    private Group constructGroup(List<Schedule> schedules) {
        return new Group("momo 회의", 1L, Category.STUDY, _7월_1일부터_2일까지, LocalDateTime.now(),
                schedules, "", "");
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
