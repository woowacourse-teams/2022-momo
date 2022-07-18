package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.group.fixture.GroupFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.group.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.domain.participant.GroupParticipantRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.dto.response.MemberResponse;

@Transactional
@SpringBootTest
class GroupParticipantServiceTest {

    @Autowired
    private GroupParticipantService groupParticipantService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(new Member("회원", "password", "momo"));
    }

    private Group saveGroup() {
        return groupRepository.save(new Group("모모의 스터디", savedMember.getId(), Category.STUDY,
                _7월_1일부터_2일까지, _6월_30일_23시_59분, List.of(_7월_1일_10시부터_12시까지.newInstance()), "", ""));
    }

    @DisplayName("모임에 참여한다")
    @Test
    void participate() {
        Group savedGroup = saveGroup();

        groupParticipantService.participate(savedGroup.getId(), savedMember.getId());

        List<MemberResponse> participants = groupParticipantService.findParticipants(savedGroup.getId());

        assertThat(participants).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(savedMember));
    }

    @DisplayName("존재하지 않는 모임에 참여할 수 없다")
    @Test
    void participateNotExistGroup() {
        assertThatThrownBy(() -> groupParticipantService.participate(0L, savedMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("존재하지 않는 사용자는 모임에 참여할 수 없다")
    @Test
    void participateNotExistMember() {
        Group savedGroup = saveGroup();

        assertThatThrownBy(() -> groupParticipantService.participate(savedGroup.getId(), 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @DisplayName("모임의 참여자 목록을 조회한다")
    @Test
    void findParticipants() {
        Group savedGroup = saveGroup();
        groupParticipantService.participate(savedGroup.getId(), savedMember.getId());

        List<MemberResponse> actual = groupParticipantService.findParticipants(savedGroup.getId());

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(savedMember));
    }

    @DisplayName("존재하지 않는 모임의 참여자 목록을 조회할 수 없다")
    @Test
    void findParticipantsNotExistGroup() {
        assertThatThrownBy(() -> groupParticipantService.findParticipants(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }
}
