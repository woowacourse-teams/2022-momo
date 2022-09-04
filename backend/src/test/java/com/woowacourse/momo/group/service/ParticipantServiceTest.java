package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;

@Transactional
@SpringBootTest
class ParticipantServiceTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private GroupManageService groupManageService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    private Password password;
    private Member host;
    private Member participant1;
    private Member participant2;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        host = memberRepository.save(new Member(UserId.momo("주최자"), password, "momo"));
        participant1 = memberRepository.save(new Member(UserId.momo("회원1"), password, "momo1"));
        participant2 = memberRepository.save(new Member(UserId.momo("회원2"), password, "momo2"));
    }

    private Group saveGroup() {
        return saveGroupWithSetCapacity(10);
    }

    private Group saveGroupWithSetCapacity(int capacity) {
        Group group = MOMO_STUDY.builder()
                .capacity(capacity)
                .toGroup(host);

        return groupRepository.save(group);
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
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("존재하지 않는 사용자는 모임에 참여할 수 없다")
    @Test
    void participateNotExistMember() {
        Group savedGroup = saveGroup();

        assertThatThrownBy(() -> participantService.participate(savedGroup.getId(), 0L))
                .isInstanceOf(MomoException.class)
                .hasMessage("멤버가 존재하지 않습니다.");
    }

    @DisplayName("모임에 이미 속해있을 경우 모임에 참여할 수 없다")
    @Test
    void reParticipate() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());

        assertThatThrownBy(() -> participantService.participate(savedGroup.getId(), participant1.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자는 본인이 참여한 모임에 재참여할 수 없습니다.");
    }

    @DisplayName("모임 정원이 가득 찬 경우 참여를 할 수 없다")
    @Test
    void participateFullGroup() {
        int capacity = 2;
        Group savedGroup = saveGroupWithSetCapacity(capacity);
        participantService.participate(savedGroup.getId(), participant1.getId());

        assertThatThrownBy(() -> participantService.participate(savedGroup.getId(), participant2.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여인원이 가득 찼습니다.");
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

        assertThat(actual).contains(participant1.getUserName());
    }

    @DisplayName("존재하지 않는 모임의 참여자 목록을 조회할 수 없다")
    @Test
    void findParticipantsNotExistGroup() {
        assertThatThrownBy(() -> participantService.findParticipants(0L))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("탈퇴한 사용자가 속한 참여자 목록을 조회할 경우 유령 계정이 보여진다")
    @Test
    void findParticipantsExistGhost() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());
        savedGroup.closeEarly();

        memberService.deleteById(participant1.getId());

        List<String> names = participantService.findParticipants(savedGroup.getId())
            .stream()
            .map(MemberResponse::getName)
            .collect(Collectors.toList());
        assertThat(names).contains("알 수 없음");
    }

    @DisplayName("모임에 탈퇴한다")
    @Test
    void leave() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());
        synchronize();

        participantService.leave(savedGroup.getId(), participant1.getId());

        List<Long> participantIds = participantService.findParticipants(savedGroup.getId())
            .stream()
            .map(MemberResponse::getId)
            .collect(Collectors.toList());
        assertThat(participantIds).doesNotContain(participant1.getId());
    }

    @DisplayName("모임의 주최자일 경우 탈퇴할 수 없다")
    @Test
    void leaveHost() {
        Group savedGroup = saveGroup();
        synchronize();

        assertThatThrownBy(() -> participantService.leave(savedGroup.getId(), host.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("주최자는 모임에 탈퇴할 수 없습니다.");
    }

    @DisplayName("모임에 참여하지 않았으면 탈퇴할 수 없다")
    @Test
    void deleteNotParticipant() {
        Group savedGroup = saveGroup();
        synchronize();

        assertThatThrownBy(() -> participantService.leave(savedGroup.getId(), participant1.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("모임의 참여자가 아닙니다.");
    }

    @DisplayName("모집 마감이 끝난 모임에는 탈퇴할 수 없다")
    @Test
    void leaveDeadline() throws IllegalAccessException {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());

        GroupFixture.setDeadlinePast(savedGroup, 1);

        assertThatThrownBy(() -> participantService.leave(savedGroup.getId(), participant1.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("모집이 마감된 모임입니다.");
    }

    @DisplayName("조기 종료된 모임에는 탈퇴할 수 없다")
    @Test
    void deleteEarlyClosed() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());
        groupManageService.closeEarly(host.getId(), savedGroup.getId());

        assertThatThrownBy(() -> participantService.leave(savedGroup.getId(), participant1.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("조기종료된 모임입니다.");
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
