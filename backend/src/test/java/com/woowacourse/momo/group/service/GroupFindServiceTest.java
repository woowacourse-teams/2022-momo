package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.DateTimeFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;

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

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(new Member("momo@woowa.com", "qwe123!@#", "momo"));
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findGroup() {
        Group expected = groupRepository.save(new Group("모모의 스터디", savedMember.getId(), Category.STUDY,
                _7월_1일부터_2일까지.newInstance(), _6월_30일_23시_59분.getInstance(), List.of(_7월_1일_10시부터_12시까지.newInstance()), "", ""));

        Group actual = groupFindService.findGroup(expected.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findGroups() {
        int count = 3;
        List<Group> expected = IntStream.rangeClosed(0, count)
                .mapToObj(i -> groupRepository.save(new Group("모모의 스터디", savedMember.getId(), Category.STUDY,
                        _7월_1일부터_2일까지.newInstance(), _6월_30일_23시_59분.getInstance(), List.of(_7월_1일_10시부터_12시까지.newInstance()), "", "")))
                .collect(Collectors.toList());

        List<Group> actual = groupFindService.findGroups();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
