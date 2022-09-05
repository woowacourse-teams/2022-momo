package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class ParticipantServiceTest {

    private final ParticipantService participantService;
    private final GroupManageService groupManageService;
    private final MemberService memberService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    private Group group;
    private Member host;
    private Member participant;

    @BeforeEach
    void setUp() {
        this.host = memberRepository.save(MOMO.toMember());
        this.group = groupRepository.save(MOMO_STUDY.toGroup(host));
        this.participant = memberRepository.save(DUDU.toMember());
    }

    @DisplayName("모임에 참여한다")
    @Test
    void participate() {
        long groupId = group.getId();
        long participantId = participant.getId();

        participantService.participate(groupId, participantId);

        List<MemberResponse> participants = participantService.findParticipants(groupId);

        assertThat(participants).hasSize(2);
    }

    @DisplayName("존재하지 않는 모임에 참여할 수 없다")
    @Test
    void participateNotExistGroup() {
        long groupId = 0;
        long participantId = participant.getId();

        assertThatThrownBy(() -> participantService.participate(groupId, participantId))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("존재하지 않는 사용자는 모임에 참여할 수 없다")
    @Test
    void participateNotExistMember() {
        long groupId = group.getId();
        long participantId = 0;

        assertThatThrownBy(() -> participantService.participate(groupId, participantId))
                .isInstanceOf(MomoException.class)
                .hasMessage("멤버가 존재하지 않습니다.");
    }

    @DisplayName("모임 정원이 가득 찬 경우 참여를 할 수 없다")
    @Test
    void participateFullGroup() {
        Group group = groupRepository.save(
                MOMO_STUDY.builder()
                        .capacity(1)
                        .toGroup(host)
        );

        long groupId = group.getId();
        long participantId = participant.getId();

        assertThatThrownBy(() -> participantService.participate(groupId, participantId))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여인원이 가득 찼습니다.");
    }

    @DisplayName("존재하지 않는 모임의 참여자 목록을 조회할 수 없다")
    @Test
    void findParticipantsNotExistGroup() {
        long groupId = 0;

        assertThatThrownBy(() -> participantService.findParticipants(groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("모임의 주최자일 경우 탈퇴할 수 없다")
    @Test
    void leaveHost() {
        long groupId = group.getId();
        long hostId = host.getId();

        assertThatThrownBy(() -> participantService.leave(groupId, hostId))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자는 모임에 탈퇴할 수 없습니다.");
    }

    @DisplayName("모임에 참여하지 않았으면 탈퇴할 수 없다")
    @Test
    void deleteNotParticipant() {
        long groupId = group.getId();
        long participantId = participant.getId();

        assertThatThrownBy(() -> participantService.leave(groupId, participantId))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임의 참여자가 아닙니다.");
    }

    @DisplayName("참여 이후에 대한 테스트")
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    class AfterParticipateTest {

        private long groupId;
        private long hostId;
        private long participantId;

        @BeforeEach
        void setUp() {
            groupId = group.getId();
            hostId = host.getId();
            participantId = participant.getId();

            participantService.participate(groupId, participantId);
        }

        @DisplayName("모임에 이미 속해있을 경우 모임에 참여할 수 없다")
        @Test
        void reParticipate() {
            assertThatThrownBy(() -> participantService.participate(groupId, participantId))
                    .isInstanceOf(MomoException.class)
                    .hasMessage("참여자는 본인이 참여한 모임에 재참여할 수 없습니다.");
        }

        @DisplayName("모임의 참여자 목록을 조회한다")
        @Test
        void findParticipants() {
            List<String> actual = participantService.findParticipants(groupId)
                    .stream()
                    .map(MemberResponse::getName)
                    .collect(Collectors.toList());

            assertThat(actual).contains(participant.getUserName());
        }

        @DisplayName("모임에 탈퇴한다")
        @Test
        void leave() {
            participantService.leave(groupId, participantId);
            synchronize();

            List<Long> participantIds = participantService.findParticipants(groupId)
                    .stream()
                    .map(MemberResponse::getId)
                    .collect(Collectors.toList());
            assertThat(participantIds).doesNotContain(participantId);
        }

        @DisplayName("모집 마감이 끝난 모임에는 탈퇴할 수 없다")
        @Test
        void leaveDeadline() throws IllegalAccessException {
            GroupFixture.setDeadlinePast(group, 1);
            synchronize();

            assertThatThrownBy(() -> participantService.leave(groupId, participantId))
                    .isInstanceOf(MomoException.class)
                    .hasMessage("모집이 마감된 모임입니다.");
        }

        @DisplayName("조기 종료된 모임에는 탈퇴할 수 없다")
        @Test
        void deleteEarlyClosed() {
            groupManageService.closeEarly(hostId, groupId);
            synchronize();

            assertThatThrownBy(() -> participantService.leave(groupId, participantId))
                    .isInstanceOf(MomoException.class)
                    .hasMessage("조기종료된 모임입니다.");
        }

        @DisplayName("탈퇴한 사용자가 속한 참여자 목록을 조회할 경우 유령 계정이 보여진다")
        @Test
        void findParticipantsExistGhost() {
            group.closeEarly();
            memberService.deleteById(participantId);
            synchronize();

            List<String> names = participantService.findParticipants(groupId)
                    .stream()
                    .map(MemberResponse::getName)
                    .collect(Collectors.toList());
            assertThat(names).contains("알 수 없음");
        }
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
