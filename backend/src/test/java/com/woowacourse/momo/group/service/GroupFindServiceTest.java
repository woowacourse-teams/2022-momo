package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.category.domain.Category.STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.이틀후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.일주일후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupFindServiceTest {

    private final GroupFindService groupFindService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    private Member host;
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;
    private Group group5;
    private Group group6;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(MOMO.toMember());
        group1 = groupRepository.save(constructGroup("모모의 스터디", host, STUDY, 5, 이틀후_23시_59분까지));
        group2 = groupRepository.save(constructGroup("모모의 술파티", host, Category.DRINK, 15, 내일_23시_59분까지));
        group3 = groupRepository.save(constructGroup("모모의 헬스클럽", host, Category.HEALTH, 1, 일주일후_23시_59분까지));

        Member anotherHost = memberRepository.save(DUDU.toMember());
        group4 = groupRepository.save(constructGroup("두두의 스터디", anotherHost, STUDY, 6, 내일_23시_59분까지));
        group5 = groupRepository.save(constructGroup("두두의 스터디", anotherHost, STUDY, 10, 이틀후_23시_59분까지));
        group5.participate(host);

        Group group = constructGroup("두두의 스터디", anotherHost, STUDY, 10, 이틀후_23시_59분까지);
        GroupFixture.setDeadlinePast(group, 1);
        group6 = groupRepository.save(group);
    }

    private Group constructGroup(String name, Member host, Category category, int capacity, DeadlineFixture deadline) {
        return MOMO_STUDY.builder()
                .name(name)
                .category(category)
                .capacity(capacity)
                .deadline(deadline)
                .duration(일주일후_하루동안)
                .schedules(일주일후_10시부터_12시까지)
                .toGroup(host);
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findGroup() {
        Group actual = groupFindService.findGroup(group1.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(group1);
    }

    @DisplayName("참여한 모임 목록을 조회한다")
    @Test
    void findParticipatedGroups() {
        List<Group> actual = groupFindService.findParticipatedGroups(host);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group1, group2, group3, group5));
    }
}
