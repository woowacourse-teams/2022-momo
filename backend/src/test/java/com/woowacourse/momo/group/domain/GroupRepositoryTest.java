package com.woowacourse.momo.group.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupSearchRepository groupSearchRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    private Password password;
    private Member host;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        host = memberRepository.save(new Member(UserId.momo("모임주최자"), password, UserName.from("momo")));
    }

    @DisplayName("스케쥴이 지정된 모임을 저장한다")
    @Test
    void saveGroupWithSchedules() {
        Group group = constructGroup(host, List.of(이틀후_10시부터_12시까지));

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
        Group group = constructGroup(host, Collections.emptyList());

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
        Group group = constructGroup(host, List.of(이틀후_10시부터_12시까지, 일주일후_10시부터_12시까지));
        Group savedGroup = groupRepository.save(group);

        Optional<Group> foundGroup = groupSearchRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get()).usingRecursiveComparison()
                .isEqualTo(savedGroup);
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void deleteById() {
        Group group = constructGroup(host, List.of(이틀후_10시부터_12시까지));

        groupRepository.save(group);
        synchronize();

        groupRepository.deleteById(group.getId());
        synchronize();

        Optional<Group> foundGroup = groupSearchRepository.findById(group.getId());

        assertThat(foundGroup).isEmpty();
    }

    @DisplayName("모임에 참여자를 추가한다")
    @Test
    void saveParticipant() {
        Member participant = memberRepository.save(new Member(UserId.momo("momo"), password, UserName.from("모모1")));
        Group savedGroup = groupRepository.save(constructGroup(host, Collections.emptyList()));

        savedGroup.participate(participant);

        Optional<Group> foundGroup = groupSearchRepository.findById(savedGroup.getId());

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getParticipants()).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(host, participant));
    }

    private Group constructGroup(Member host, List<ScheduleFixture> schedules) {
        return MOMO_STUDY.builder()
                .schedules(schedules)
                .toGroup(host);
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
