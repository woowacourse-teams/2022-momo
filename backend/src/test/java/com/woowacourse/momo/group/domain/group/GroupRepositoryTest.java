package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

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

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    private Password password;
    private Member host;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        host = memberRepository.save(new Member(UserId.momo("주최자"), password, "momo"));
    }

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        List<Schedule> schedules = List.of(이틀후_10시부터_12시까지.getSchedule());
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
        List<Schedule> schedules = List.of(이틀후_10시부터_12시까지.getSchedule(), 일주일후_10시부터_12시까지.getSchedule());
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
        Group group1 = constructGroup(host, List.of(이틀후_10시부터_12시까지.getSchedule()));
        Group group2 = constructGroup(host, List.of(일주일후_10시부터_12시까지.getSchedule()));
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
        List<Schedule> schedules = List.of(이틀후_10시부터_12시까지.getSchedule());
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
        Member participant = memberRepository.save(new Member(UserId.momo("momo"), password, "모모1"));
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
        Member participant = memberRepository.save(new Member(UserId.momo("momo"), password, "모모1"));
        Group savedGroup = groupRepository.save(constructGroup(host, Collections.emptyList()));

        savedGroup.participate(participant);
        synchronize();

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getParticipants()).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(host, participant));
    }

    private Group constructGroup(Member host, List<Schedule> schedules) {
        return constructGroup("momo 회의", host, schedules);
    }

    private Group constructGroup(String name, Member host, List<Schedule> schedules) {
        return new Group(new GroupName(name), host, Category.STUDY, new Capacity(10), 이틀후부터_5일동안.getDuration(),
                new Deadline(내일_23시_59분.getDateTime()), new Schedules(schedules), "", "");
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
