package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.DateTimeFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._7월_2일_10시부터_12시까지;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    private Member host;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(new Member("주최자", "password", "momo"));
    }

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        List<Schedule> schedules = List.of(_7월_1일_10시부터_12시까지.newInstance());
        Group group = constructGroup(host, schedules);

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
        Group group = constructGroup(host, schedules);

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
        Group group = constructGroup(host, schedules);
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
        Group group1 = constructGroup(host, List.of(_7월_1일_10시부터_12시까지.newInstance()));
        Group group2 = constructGroup(host, List.of(_7월_2일_10시부터_12시까지.newInstance()));
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
        Group group = constructGroup(host, schedules);
        groupRepository.save(group);

        synchronize();

        groupRepository.deleteById(group.getId());

        synchronize();

        Optional<Group> foundGroup = groupRepository.findById(group.getId());

        assertThat(foundGroup).isEmpty();
    }

    @DisplayName("식별자를 통해 참여자가 있는 모임을 삭제한다")
    @Test
    void deleteIncludedParticipants() {
        Member participant = memberRepository.save(new Member("email1@woowacourse.com", "1234asdf!", "모모1"));
        Group savedGroup = groupRepository.save(constructGroup(host, Collections.emptyList()));

        savedGroup.participate(participant);
        synchronize();

        groupRepository.deleteById(savedGroup.getId());
        synchronize();

        Optional<Group> deletedGroup = groupRepository.findById(savedGroup.getId());
        assertThat(deletedGroup).isEmpty();
    }

    @DisplayName("모임에 참여자를 추가한다")
    @Test
    void saveParticipant() {
        Member participant = memberRepository.save(new Member("email1@woowacourse.com", "1234asdf!", "모모1"));
        Group savedGroup = groupRepository.save(constructGroup(host, Collections.emptyList()));

        savedGroup.participate(participant);
        synchronize();

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getParticipants()).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(participant));
    }

    private Group constructGroup(Member host, List<Schedule> schedules) {

        return new Group("momo 회의", host, Category.STUDY, 10, _7월_1일부터_2일까지.getInstance(), _6월_30일_23시_59분.getInstance(),
                schedules, "", "");
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
