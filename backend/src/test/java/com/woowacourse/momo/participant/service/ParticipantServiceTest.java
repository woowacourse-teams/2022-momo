package com.woowacourse.momo.participant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.ScheduleFixture.이틀후_10시부터_12시까지;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.response.MemberResponse;

@Transactional
@SpringBootTest
class ParticipantServiceTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

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
                이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(), List.of(이틀후_10시부터_12시까지.newInstance()),
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
                .hasMessage("마감된 모임에는 참여할 수 없습니다.");
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
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("탈퇴한 사용자가 속한 참여자 목록을 조회할 경우 유령 계정이 보여진다")
    @Test
    void findParticipantsExistGhost() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());
        memberService.deleteById(participant1.getId());

        List<String> names = participantService.findParticipants(savedGroup.getId())
            .stream()
            .map(MemberResponse::getName)
            .collect(Collectors.toList());
        assertThat(names).contains("알 수 없음");
    }

    @DisplayName("모임에 탈퇴한다")
    @Test
    void delete() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());
        synchronize();

        participantService.delete(savedGroup.getId(), participant1.getId());
        synchronize();

        List<Long> participantIds = participantService.findParticipants(savedGroup.getId())
            .stream()
            .map(MemberResponse::getId)
            .collect(Collectors.toList());
        assertThat(participantIds).doesNotContain(participant1.getId());
    }

    @DisplayName("모임의 주최자일 경우 탈퇴할 수 없다")
    @Test
    void deleteHost() {
        Group savedGroup = saveGroup();
        synchronize();

        assertThatThrownBy(() -> participantService.delete(savedGroup.getId(), host.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("주최자는 모임에 탈퇴할 수 없습니다.");
    }

    @DisplayName("모임에 참여하지 않았으면 탈퇴할 수 없다")
    @Test
    void deleteNotParticipant() {
        Group savedGroup = saveGroup();
        synchronize();

        assertThatThrownBy(() -> participantService.delete(savedGroup.getId(), participant1.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("모임의 참여자가 아닙니다.");
    }

    @DisplayName("모집 마감이 끝난 모임에는 탈퇴할 수 없다")
    @Test
    void deleteDeadline() throws IllegalAccessException {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        setPastDeadline(savedGroup, yesterday);

        assertThatThrownBy(() -> participantService.delete(savedGroup.getId(), participant1.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("모집이 마감된 모임입니다.");
    }

    @DisplayName("조기 종료된 모임에는 탈퇴할 수 없다")
    @Test
    void deleteEarlyClosed() {
        Group savedGroup = saveGroup();
        participantService.participate(savedGroup.getId(), participant1.getId());
        groupService.closeEarly(host.getId(), savedGroup.getId());

        assertThatThrownBy(() -> participantService.delete(savedGroup.getId(), participant1.getId()))
            .isInstanceOf(MomoException.class)
            .hasMessage("조기종료된 모임입니다.");
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }

    private void setPastDeadline(Group group, LocalDateTime date) throws IllegalAccessException {
        LocalDateTime original = LocalDateTime.of(group.getDuration().getStartDate().minusDays(1), LocalTime.now());
        Deadline deadline = new Deadline(original);
        Calendar calendar = new Calendar(group.getSchedules(), group.getDuration(), original);

        int index = 0;
        Class<Deadline> clazzDeadline = Deadline.class;
        Field[] fieldDeadline = clazzDeadline.getDeclaredFields();
        fieldDeadline[index].setAccessible(true);
        fieldDeadline[index].set(deadline, date);

        int calendarField = 2;
        Class<Calendar> clazzCalendar = Calendar.class;
        Field[] fieldCalendar = clazzCalendar.getDeclaredFields();
        fieldCalendar[calendarField].setAccessible(true);
        fieldCalendar[calendarField].set(calendar, deadline);

        int deadlineField = 6;
        Class<Group> clazzGroup = Group.class;
        Field[] fieldGroup = clazzGroup.getDeclaredFields();
        fieldGroup[deadlineField].setAccessible(true);
        fieldGroup[deadlineField].set(group, calendar);
    }
}
