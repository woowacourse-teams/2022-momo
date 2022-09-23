package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.LocationFixture.선릉캠퍼스;
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
import com.woowacourse.momo.fixture.LocationFixture;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.Location;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.request.LocationRequest;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupModifyServiceTest {

    private final GroupModifyService groupModifyService;
    private final GroupSearchService groupSearchService;
    private final GroupRepository groupRepository;
    private final GroupSearchRepository groupSearchRepository;
    private final MemberRepository memberRepository;
    private final ParticipateService participateService;

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
        long groupId = groupModifyService.create(host.getId(), request)
                .getGroupId();

        Optional<Group> group = groupSearchRepository.findById(groupId);
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

        assertThatThrownBy(() -> groupModifyService.create(hostId, request))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }

    @DisplayName("모임을 수정한다")
    @Test
    void update() {
        GroupFixture target = DUDU_STUDY;

        long groupId = this.group.getId();
        GroupUpdateRequest request = target.toUpdateRequest();
        groupModifyService.update(host.getId(), groupId, request);

        Optional<Group> group = groupSearchRepository.findById(groupId);
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

        GroupUpdateRequest request = MOMO_STUDY.toUpdateRequest();
        assertThatThrownBy(() -> groupModifyService.update(hostId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateMemberIsNotHost() {
        long groupId = group.getId();
        long participantId = participant.getId();

        GroupUpdateRequest request = DUDU_STUDY.toUpdateRequest();
        assertThatThrownBy(() -> groupModifyService.update(participantId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자가 아닙니다.");
    }

    @DisplayName("주최자 외 참여자가 있을 때 모임을 수정하면 예외가 발생한다")
    @Test
    void updateExistParticipants() {
        long groupId = group.getId();
        long hostId = host.getId();

        group.participate(participant);

        GroupUpdateRequest request = DUDU_STUDY.toUpdateRequest();
        assertThatThrownBy(() -> groupModifyService.update(hostId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임은 참여자가 존재합니다.");
    }

    @DisplayName("모집 마감된 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateFinishedRecruitmentGroup() {
        long groupId = group.getId();
        long hostId = host.getId();

        group.participate(participant);

        GroupUpdateRequest request = DUDU_STUDY.toUpdateRequest();
        assertThatThrownBy(() -> groupModifyService.update(hostId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임은 참여자가 존재합니다.");
    }

    @DisplayName("모임 장소를 업데이트한다")
    @Test
    void updateLocation() {
        LocationFixture target = 선릉캠퍼스;

        long groupId = this.group.getId();
        LocationRequest request = target.toRequest();
        groupModifyService.updateLocation(host.getId(), groupId, request);

        Optional<Group> group = groupSearchRepository.findById(groupId);
        assertThat(group).isPresent();

        Location actual = group.get().getLocation();
        Location expected = target.toLocation();
        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("모임을 조기 마감한다")
    @Test
    void closeEarly() {
        long groupId = group.getId();
        long hostId = host.getId();

        groupModifyService.closeEarly(hostId, groupId);

        boolean actual = groupSearchService.findGroup(groupId, null).isFinished();
        assertThat(actual).isTrue();
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 조기마감 할 경우 예외가 발생한다")
    @Test
    void closeEarlyMemberIsNotHost() {
        long groupId = group.getId();
        long participantId = participant.getId();

        assertThatThrownBy(() -> groupModifyService.closeEarly(participantId, groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자가 아닙니다.");
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void delete() {
        long groupId = group.getId();
        long hostId = host.getId();

        groupModifyService.delete(hostId, groupId);

        assertThatThrownBy(() -> groupSearchService.findGroup(groupId, null))
                .isInstanceOf(GroupException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteNotHost() {
        long groupId = group.getId();
        long participantId = participant.getId();

        assertThatThrownBy(() -> groupModifyService.delete(participantId, groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자가 아닙니다.");
    }

    @DisplayName("주최자를 제외하고 참여자가 있을 경우 모임을 삭제하면 예외가 발생한다")
    @Test
    void deleteExistParticipants() {
        long groupId = group.getId();
        long hostId = host.getId();

        participateService.participate(group.getId(), participant.getId());

        assertThatThrownBy(() -> groupModifyService.delete(hostId, groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임은 참여자가 존재합니다.");
    }

    @DisplayName("모집 마감된 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteFinishedRecruitmentGroup() {
        long groupId = group.getId();
        long hostId = host.getId();

        group.participate(participant);

        assertThatThrownBy(() -> groupModifyService.delete(hostId, groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임은 참여자가 존재합니다.");
    }
}
