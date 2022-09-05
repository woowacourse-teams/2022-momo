package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.service.request.GroupRequest;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupManageServiceTest {

    private final GroupManageService groupManageService;
    private final GroupSearchService groupSearchService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final ParticipantService participantService;

    private Group group;
    private Member host;
    private Member participant;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(MOMO.toMember());
        group = groupRepository.save(MOMO_STUDY.toGroup(host));

        participant = memberRepository.save(DUDU.toMember());
    }

    @DisplayName("모임을 생성한다")
    @Test
    void create() {
        GroupRequest request = MOMO_STUDY.toRequest();
        long groupId = groupManageService.create(host.getId(), request)
                .getGroupId();

        Optional<Group> group = groupRepository.findById(groupId);
        assertThat(group).isPresent();

        Group actual = group.get();
        Group expected = MOMO_STUDY.toGroup(host);
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(groupId),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getCategory()).isEqualTo(expected.getCategory()),
                () -> assertThat(actual.getCapacity()).isEqualTo(expected.getCapacity()),
                () -> assertThat(actual.getLocation()).isEqualTo(expected.getLocation()),
                () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription()),
                () -> assertThat(actual.getDeadline()).isEqualTo(expected.getDeadline()),
                () -> assertThat(actual.getDuration()).isEqualTo(expected.getDuration()),
                () -> {
                    assertThat(actual.getSchedules()).hasSize(expected.getSchedules().size());

                    for (int i = 0; i < expected.getSchedules().size(); i++) {
                        assertThat(actual.getSchedules().get(i)).usingRecursiveComparison()
                                .ignoringFields("id")
                                .isEqualTo(expected.getSchedules().get(i));
                    }
                }
        );
    }

    @DisplayName("유효하지 않은 카테고리로 모임을 생성하면 예외가 발생한다")
    @Test
    void createWithInvalidCategoryId() {
        long hostId = host.getId();

        GroupRequest request = MOMO_STUDY.builder()
                .category(0)
                .toRequest();

        assertThatThrownBy(() -> groupManageService.create(hostId, request))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }

    @DisplayName("모임을 수정한다")
    @Test
    void update() {
        GroupFixture target = DUDU_STUDY;

        long groupId = this.group.getId();
        GroupRequest request = target.toRequest();
        groupManageService.update(host.getId(), groupId, request);

        Optional<Group> group = groupRepository.findById(groupId);
        assertThat(group).isPresent();

        Group actual = group.get();
        Group expected = target.toGroup(host);
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(groupId),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getCategory()).isEqualTo(expected.getCategory()),
                () -> assertThat(actual.getCapacity()).isEqualTo(expected.getCapacity()),
                () -> assertThat(actual.getLocation()).isEqualTo(expected.getLocation()),
                () -> assertThat(actual.getDescription()).isEqualTo(expected.getDescription()),
                () -> assertThat(actual.getDeadline()).isEqualTo(expected.getDeadline()),
                () -> assertThat(actual.getDuration()).isEqualTo(expected.getDuration()),
                () -> {
                    assertThat(actual.getSchedules()).hasSize(expected.getSchedules().size());

                    for (int i = 0; i < expected.getSchedules().size(); i++) {
                        assertThat(actual.getSchedules().get(i)).usingRecursiveComparison()
                                .ignoringFields("id")
                                .isEqualTo(expected.getSchedules().get(i));
                    }
                }
        );
    }

    @DisplayName("존재하지 않는 모임을 수정하는 경우 예외가 발생한다")
    @Test
    void updateNotExistGroup() {
        long groupId = 1000;
        long hostId = host.getId();

        GroupRequest request = MOMO_STUDY.toRequest();
        assertThatThrownBy(() -> groupManageService.update(hostId, groupId, request))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임ㅇ르 수정할 경우 예외가 발생한다")
    @Test
    void updateMemberIsNotHost() {
        long groupId = group.getId();
        long participantId = participant.getId();

        GroupRequest request = DUDU_STUDY.toRequest();
        assertThatThrownBy(() -> groupManageService.update(participantId, groupId, request))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다.");
    }

    @DisplayName("주최자 외 참여자가 있을 때 모임을 수정하면 예외가 발생한다")
    @Test
    void updateExistParticipants() {
        long groupId = group.getId();
        long hostId = host.getId();

        group.participate(participant);

        GroupRequest request = DUDU_STUDY.toRequest();
        assertThatThrownBy(() -> groupManageService.update(hostId, groupId, request))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모집 마김된 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateFinishedRecruitmentGroup() {
        long groupId = group.getId();
        long hostId = host.getId();

        group.participate(participant);

        GroupRequest request = DUDU_STUDY.toRequest();
        assertThatThrownBy(() -> groupManageService.update(hostId, groupId, request))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모임을 조기 마감한다")
    @Test
    void closeEarly() {
        long groupId = group.getId();
        long hostId = host.getId();

        groupManageService.closeEarly(hostId, groupId);

        boolean actual = groupSearchService.findGroup(groupId).isFinished();
        assertThat(actual).isTrue();
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 조기마감 할 경우 예외가 발생한다")
    @Test
    void closeEarlyMemberIsNotHost() {
        long groupId = group.getId();
        long participantId = participant.getId();

        assertThatThrownBy(() -> groupManageService.closeEarly(participantId, groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다.");
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void delete() {
        long groupId = group.getId();
        long hostId = host.getId();

        groupManageService.delete(hostId, groupId);

        assertThatThrownBy(() -> groupSearchService.findGroup(groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteNotHost() {
        long groupId = group.getId();
        long participantId = participant.getId();

        assertThatThrownBy(() -> groupManageService.delete(participantId, groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다.");
    }

    @DisplayName("주최자를 제외하고 참여자가 있을 경우 모임을 삭제하면 예외가 발생한다")
    @Test
    void deleteExistParticipants() {
        long groupId = group.getId();
        long hostId = host.getId();

        participantService.participate(group.getId(), participant.getId());

        assertThatThrownBy(() -> groupManageService.delete(hostId, groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모집 마김된 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteFinishedRecruitmentGroup() {
        long groupId = group.getId();
        long hostId = host.getId();

        group.participate(participant);

        assertThatThrownBy(() -> groupManageService.delete(hostId, groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다.");
    }
}
