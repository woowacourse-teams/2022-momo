package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.ScheduleFixture.이틀후_10시부터_12시까지;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@SpringBootTest
public class GroupFindServiceTest {

    @Autowired
    private GroupFindService groupFindService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    private Member savedAnotherMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(new Member("momo", "qwe123!@#", "momo"));
        savedAnotherMember = memberRepository.save(new Member("mumu", "qwe123!@#", "mumu"));
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findGroup() {
        Group expected = groupRepository.save(new Group("모모의 스터디", savedMember, Category.STUDY, 10,
                이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(), List.of(이틀후_10시부터_12시까지.newInstance()),
                "", ""));

        Group actual = groupFindService.findGroup(expected.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findGroups() {
        int count = 3;
        List<Group> expected = IntStream.range(0, count)
                .mapToObj(i -> groupRepository.save(new Group("모모의 스터디", savedMember, Category.STUDY, 10,
                        이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(),
                        List.of(이틀후_10시부터_12시까지.newInstance()), "", "")))
                .collect(Collectors.toList());

        List<Group> actual = groupFindService.findGroups();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("참여하거나 가입한 모임 목록을 조회한다")
    @Test
    void findRelatedGroups() {
        int count = 3;
        IntStream.range(0, count)
                .forEach(i -> groupRepository.save(new Group("모모의 스터디", savedMember, Category.STUDY, 10,
                        이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(),
                        List.of(이틀후_10시부터_12시까지.newInstance()), "", "")));

        groupRepository.save(new Group("모모의 스터디", savedAnotherMember, Category.STUDY, 10,
                이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(),
                List.of(이틀후_10시부터_12시까지.newInstance()), "", ""));

        assertThat(groupFindService.findRelatedGroups(savedMember.getId()))
                .hasSize(count);
    }
}
