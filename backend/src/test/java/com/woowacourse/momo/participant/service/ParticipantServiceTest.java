package com.woowacourse.momo.participant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateTimeFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;
import com.woowacourse.momo.member.exception.NotFoundMemberException;

@Transactional
@SpringBootTest
class ParticipantServiceTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member host;
    private Member participant1;
    private Member participant2;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(new Member("주최자", "password", "momo"));
        participant1 = memberRepository.save(new Member("회원1", "password", "momo2"));
        participant2 = memberRepository.save(new Member("회원2", "password", "momo2"));
    }

    private Group saveGroup() {
        return saveGroupWithSetCapacity(10);
    }

    private Group saveGroupWithSetCapacity(int capacity) {
        return groupRepository.save(new Group("모모의 스터디", host, Category.STUDY, capacity,
                _7월_1일부터_2일까지.getInstance(), _6월_30일_23시_59분.getInstance(), List.of(_7월_1일_10시부터_12시까지.newInstance()),
                "", ""));
    }

    @DisplayName("모임에 참여한다")
    @Test
    void participate() {
        Group savedGroup = saveGroup();

        participantService.participate(savedGroup.getId(), participant1.getId());

        List<MemberResponse> participants = participantService.findParticipants(savedGroup.getId());

        assertThat(participants).hasSize(2);
    }

    @DisplayName("존재하지 않는 모임에 참여할 수 없다")
    @Test
    void participateNotExistGroup() {
        assertThatThrownBy(() -> participantService.participate(0L, participant1.getId()))
                .isInstanceOf(NotFoundGroupException.class);
    }

    @DisplayName("존재하지 않는 사용자는 모임에 참여할 수 없다")
    @Test
    void participateNotExistMember() {
        Group savedGroup = saveGroup();

        assertThatThrownBy(() -> participantService.participate(savedGroup.getId(), 0L))
                .isInstanceOf(NotFoundMemberException.class);
    }

    @DisplayName("모임에 이미 속해있을 경우 모임에 참여할 수 없다")
    @Test
    void reParticipate() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());

        assertThatThrownBy(() -> participantService.participate(savedGroup.getId(), participant1.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 참여한 모임입니다.");
    }

    @DisplayName("모임 정원이 가득 찬 경우 참여를 할 수 없다")
    @Test
    void participateFullGroup() {
        int capacity = 2;
        Group savedGroup = saveGroupWithSetCapacity(capacity);
        participantService.participate(savedGroup.getId(), participant1.getId());

        assertThatThrownBy(() -> participantService.participate(savedGroup.getId(), participant2.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정원이 가득 찼습니다.");
    }

    @DisplayName("모임의 참여자 목록을 조회한다")
    @Test
    void findParticipants() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());

        List<String> actual = participantService.findParticipants(savedGroup.getId())
                .stream()
                .map(MemberResponse::getName)
                .collect(Collectors.toList());

        assertThat(actual).contains(participant1.getName());
    }

    @DisplayName("존재하지 않는 모임의 참여자 목록을 조회할 수 없다")
    @Test
    void findParticipantsNotExistGroup() {
        assertThatThrownBy(() -> participantService.findParticipants(0L))
                .isInstanceOf(NotFoundGroupException.class);
    }
}
