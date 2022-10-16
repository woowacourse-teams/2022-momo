package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.내일_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;


@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class ScheduleRepositoryTest {

    private final GroupRepository groupRepository;
    private final GroupSearchRepository groupSearchRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final EntityManager entityManager;

    Member member;
    Group group;
    Schedule schedule1;
    Schedule schedule2;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MOMO.toMember());
        this.member = member;

        group = groupRepository.save(MOMO_STUDY.toGroup(member));
        schedule1 = 내일_10시부터_12시까지.toSchedule();
        schedule2 = 이틀후_10시부터_12시까지.toSchedule();
        group.addSchedule(schedule1);
        group.addSchedule(schedule2);
        synchronize();
    }

    @DisplayName("모임 ID를 통해 연관된 일정을 모두 삭제한다")
    @Test
    void deleteAllByGroupId() {
        scheduleRepository.deleteAllByGroupId(group.getId());

        Optional<Group> foundGroup = groupSearchRepository.findById(group.getId());
        assertThat(foundGroup).isPresent();

        List<Schedule> expected = foundGroup.get().getSchedules();
        assertThat(expected).isEmpty();
    }

    @DisplayName("입력받은 Id의 일정을 모두 삭제한다")
    @Test
    void deleteAllInScheduleIds() {
        scheduleRepository.deleteAllInSchedules(List.of(schedule1, schedule2));

        Optional<Group> foundGroup = groupSearchRepository.findById(group.getId());
        assertThat(foundGroup).isPresent();

        List<Schedule> expected = foundGroup.get().getSchedules();
        assertThat(expected).isEmpty();
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
