package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.GroupFixture.DUDU_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.GUGU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.내일_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.내일_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;
import static com.woowacourse.momo.group.exception.GroupErrorCode.SCHEDULE_MUST_BE_INCLUDED_IN_DURATION;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.fixture.GroupFixture.Builder;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.search.GroupSearchRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupModifyServiceTest {

    private final GroupModifyService groupModifyService;
    private final GroupSearchService groupSearchService;
    private final GroupSearchRepository groupSearchRepository;
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    private Group group;
    private Member host;
    private Member participant1;
    private Member participant2;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(MOMO.toMember());
        GroupIdResponse groupIdResponse = groupModifyService.create(host.getId(), MOMO_STUDY.toRequest());
        group = groupSearchRepository.findById(groupIdResponse.getGroupId()).orElseThrow();

        participant1 = memberRepository.save(DUDU.toMember());
        participant2 = memberRepository.save(GUGU.toMember());

        group.participate(participant1);
        group.participate(participant2);
        synchronize();
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
        Group expectedGroup = MOMO_STUDY.toGroup(host);
        List<Schedule> expectedSchedules = MOMO_STUDY.getSchedules1();

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(groupId),
                () -> assertThat(actual.getName()).isEqualTo(expectedGroup.getName()),
                () -> assertThat(actual.getCategory()).isEqualTo(expectedGroup.getCategory()),
                () -> assertThat(actual.getCapacity()).isEqualTo(expectedGroup.getCapacity()),
                () -> assertThat(actual.getLocation()).isEqualTo(expectedGroup.getLocation()),
                () -> assertThat(actual.getDescription()).isEqualTo(expectedGroup.getDescription()),
                () -> assertThat(actual.getDeadline()).isEqualTo(expectedGroup.getDeadline()),
                () -> assertThat(actual.getDuration()).isEqualTo(expectedGroup.getDuration()),
                () -> {
                    assertThat(actual.getSchedules()).hasSize(expectedSchedules.size());

                    for (int i = 0; i < 2; i++) {
                        assertThat(actual.getSchedules().get(i)).usingRecursiveComparison()
                                .ignoringFields("id", "group")
                                .isEqualTo(expectedSchedules.get(i));
                    }
                }
        );
    }

    @DisplayName("생성 시, 일정들이 기간 내에 속해있지 않을 경우 예외가 발생한다")
    @Test
    void validateSchedulesAreInDurationWhenCreate() {
        GroupRequest request = constructGroupRequest()
                .schedules(List.of(이틀후_10시부터_12시까지))
                .duration(내일_하루동안)
                .toRequest();

        assertThatThrownBy(() -> groupModifyService.create(host.getId(), request))
                .isInstanceOf(GroupException.class)
                .hasMessage(SCHEDULE_MUST_BE_INCLUDED_IN_DURATION.getMessage());
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

    @DisplayName("모임 일정을 수정한다")
    @Test
    void updateSchedules() {
        long groupId = this.group.getId();

        Builder target = constructGroupRequest()
                .schedules(List.of(내일_10시부터_12시까지, 일주일후_10시부터_12시까지));
        GroupRequest request = target.toRequest();

        groupModifyService.update(host.getId(), groupId, request);
        synchronize();

        Optional<Group> group = groupSearchRepository.findById(groupId);
        assertThat(group).isPresent();

        Group actual = group.get();
        Group expected = target.toGroupWithSchedule(host);
        assertGroup(groupId, actual, expected);
    }

    @DisplayName("모임 이름을 수정한다")
    @Test
    void updateName() {
        long groupId = this.group.getId();
        Builder target = constructGroupRequest()
                .name("모모의 스프링 스터디");
        GroupRequest request = target.toRequest();

        groupModifyService.update(host.getId(), groupId, request);
        synchronize();

        Optional<Group> group = groupSearchRepository.findById(groupId);
        assertThat(group).isPresent();

        Group actual = group.get();
        Group expected = target.toGroupWithSchedule(host);
        assertGroup(groupId, actual, expected);
    }

    void assertGroup(long groupId, Group actual, Group expected) {
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
                                .ignoringFields("id", "group")
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
        assertThatThrownBy(() -> groupModifyService.update(hostId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateMemberIsNotHost() {
        long groupId = group.getId();
        long participantId = participant1.getId();

        GroupRequest request = DUDU_STUDY.toRequest();
        assertThatThrownBy(() -> groupModifyService.update(participantId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자가 아닙니다.");
    }

    @DisplayName("주최자 외 참여자가 있을 때 모임을 수정할 수 있다")
    @Test
    void updateExistParticipants() {
        long groupId = group.getId();
        long hostId = host.getId();

        GroupRequest request = constructGroupRequest()
                .name("모모의 스프링 스터디")
                .toRequest();
        assertDoesNotThrow(() -> groupModifyService.update(hostId, groupId, request));
    }

    @DisplayName("모집 마감된 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateFinishedRecruitmentGroup() {
        long groupId = group.getId();
        long hostId = host.getId();
        groupModifyService.closeEarly(hostId, groupId);

        GroupRequest request = DUDU_STUDY.toRequest();
        assertThatThrownBy(() -> groupModifyService.update(hostId, groupId, request))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임은 조기 마감되어 있습니다.");
    }

    @DisplayName("모임을 조기 마감한다")
    @Test
    void closeEarly() {
        long groupId = group.getId();
        long hostId = host.getId();

        groupModifyService.closeEarly(hostId, groupId);

        boolean actual = groupSearchService.findGroup(groupId).isFinished();
        assertThat(actual).isTrue();
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 조기마감 할 경우 예외가 발생한다")
    @Test
    void closeEarlyMemberIsNotHost() {
        long groupId = group.getId();
        long participantId = participant1.getId();

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

        assertThatThrownBy(() -> groupSearchService.findGroup(groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteNotHost() {
        long groupId = group.getId();
        long participantId = participant1.getId();

        assertThatThrownBy(() -> groupModifyService.delete(participantId, groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자가 아닙니다.");
    }

    @DisplayName("주최자를 제외하고 참여자가 있을 경우 모임을 삭제할 수 있다")
    @Test
    void deleteExistParticipants() {
        long groupId = group.getId();
        long hostId = host.getId();

        assertDoesNotThrow(() -> groupModifyService.delete(hostId, groupId));
    }

    @DisplayName("모집 마감된 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteFinishedRecruitmentGroup() {
        long groupId = group.getId();
        long hostId = host.getId();
        groupModifyService.closeEarly(hostId, groupId);

        assertThatThrownBy(() -> groupModifyService.delete(hostId, groupId))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임은 조기 마감되어 있습니다.");
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }

    Builder constructGroupRequest() {
        return MOMO_STUDY.builder();
    }
}
