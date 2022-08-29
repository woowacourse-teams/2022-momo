package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.어제_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.일주일후_23시_59분;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;

class GroupTest {

    private static final Password PASSWORD = Password.encrypt("momo123!", new SHA256Encoder());
    private static final Member HOST = new Member(UserId.momo("주최자"), PASSWORD, "momo");
    private static final Member PARTICIPANT = new Member(UserId.momo("참여자"), PASSWORD, "momo");
    private static final Member MEMBER = new Member(UserId.momo("사용자"), PASSWORD, "momo");

    @DisplayName("유효하지 않은 모임 정원 값으로 인스턴스 생성시 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100})
    void validateOutOfCapacityRange(int capacity) {
        assertThatThrownBy(() -> constructGroupWithSetCapacity(capacity))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임 내 인원은 1명 이상 99명 이하여야 합니다.");
    }

    @DisplayName("유효한 모임 정원 값으로 인스턴스 생성시 예외가 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = {1, 99})
    void validateInRangeOfCapacity(int capacity) {
        assertDoesNotThrow(() -> constructGroupWithSetCapacity(capacity));
    }

    @DisplayName("과거의 마감기한 날짜로 인스턴스 생성시 예외가 발생한다")
    @Test
    void validatePastDeadline() {
        LocalDateTime deadline = LocalDateTime.now().minusMinutes(1);
        assertThatThrownBy(() -> constructGroupWithSetDeadline(deadline))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 과거일 수 없습니다.");
    }

    @DisplayName("유효한 마감기한 날짜로 인스턴스 생성시 예외가 발생하지 않는다")
    @Test
    void validateFutureDeadline() {
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(1);
        assertDoesNotThrow(() -> constructGroupWithSetDeadline(deadline));
    }

    @DisplayName("모임 기간 시작일 이후의 마감기한으로 인스턴스 생성시 예외가 발생한다")
    @Test
    void validateDeadlineIsBeforeStartDuration() {
        LocalDateTime deadline = 일주일후_23시_59분.getInstance();
        assertThatThrownBy(() -> constructGroupWithSetDeadline(deadline))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 시작 일자 이후일 수 없습니다.");
    }

    @DisplayName("회원이 모임의 주최자인지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsHostArguments")
    void isHost(Member member, boolean expected) {
        Group group = constructGroup();

        assertThat(group.isHost(member)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsHostArguments() {
        return Stream.of(
                Arguments.of(HOST, Boolean.TRUE),
                Arguments.of(MEMBER, Boolean.FALSE)
        );
    }

    @DisplayName("모임에 참여한다")
    @Test
    void participate() {
        Group group = constructGroup();
        group.participate(PARTICIPANT);

        assertThat(group.getParticipants()).hasSize(2);
    }

    @DisplayName("이미 참여한 모임에 참가할 경우 예외가 발생한다")
    @Test
    void validateReParticipant() {
        Group group = constructGroup();
        group.participate(PARTICIPANT);

        assertThatThrownBy(() -> group.participate(PARTICIPANT))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자는 본인이 참여한 모임에 재참여할 수 없습니다.");
    }

    @DisplayName("정원이 가득찬 모임에 참가할 경우 예외가 발생한다")
    @Test
    void validateFinishedRecruitmentWithFullCapacity() {
        int capacity = 2;
        Group group = constructGroupWithSetCapacity(capacity);
        group.participate(PARTICIPANT);

        assertThatThrownBy(() -> group.participate(MEMBER))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감된 모임에는 참여할 수 없습니다.");
    }

    @DisplayName("마감기한이 지난 모임에 참가할 경우 예외가 발생한다")
    @Test
    void validateFinishedRecruitmentWithDeadlinePassed() throws IllegalAccessException {
        Group group = constructGroupWithSetPastDeadline(어제_23시_59분.getInstance());

        assertThatThrownBy(() -> group.participate(MEMBER))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감된 모임에는 참여할 수 없습니다.");
    }

    @DisplayName("모임에 참여한 회원을 반환한다")
    @Test
    void getParticipants() {
        Group group = constructGroup();
        group.participate(PARTICIPANT);

        List<Member> participants = group.getParticipants();
        assertThat(participants).contains(HOST, PARTICIPANT);
    }

    @DisplayName("정원이 초과하지도 않았고 조기마감되지도 않았고 마감 시간이 지나지도 않았다면 종료되지 않은 모임이다")
    @Test
    void isFinishedRecruitment() {
        int capacity = 2;
        Group group = constructGroupWithSetCapacity(capacity);

        assertThat(group.isFinishedRecruitment()).isFalse();
    }

    @DisplayName("현재 참여 인원이 정원을 초과하면 모집이 종료된다")
    @Test
    void isFinishedRecruitmentWithOverCapacity() {
        int capacity = 2;
        Group group = constructGroupWithSetCapacity(capacity);
        group.participate(PARTICIPANT);

        assertThat(group.isFinishedRecruitment()).isTrue();
    }

    @DisplayName("모임이 조기마감되면 모집이 종료된다")
    @Test
    void isFinishedRecruitmentWithPassedDeadline() {
        Group group = constructGroup();
        group.closeEarly();

        assertThat(group.isFinishedRecruitment()).isTrue();
    }

    @DisplayName("모집 마감시간이 지나면 모집이 종료된다")
    @Test
    void isFinishedRecruitmentWithEarlyClosing() throws IllegalAccessException {
        Group group = constructGroupWithSetPastDeadline(어제_23시_59분.getInstance());

        assertThat(group.isFinishedRecruitment()).isTrue();
    }

    @DisplayName("모임이 조기마감되면 종료된 모임이다")
    @Test
    void isEndCloseEarly() {
        Group group = constructGroup();
        group.closeEarly();

        assertThat(group.isEnd()).isTrue();
    }

    @DisplayName("모집 마감시간이 지나면 종료된 모임이다")
    @Test
    void isEndOverDeadline() throws IllegalAccessException {
        Group group = constructGroupWithSetPastDeadline(어제_23시_59분.getInstance());

        assertThat(group.isEnd()).isTrue();
    }

    @DisplayName("모임에 참여하지 않았으면 탈퇴할 수 없다")
    @Test
    void validateLeaveNotParticipant() {
        Group group = constructGroup();

        assertThatThrownBy(() -> group.validateMemberCanLeave(PARTICIPANT))
            .isInstanceOf(MomoException.class)
            .hasMessage("모임의 참여자가 아닙니다.");
    }

    @DisplayName("모집 마감이 끝난 모임에는 탈퇴할 수 없다")
    @Test
    void validateLeaveDeadline() throws IllegalAccessException {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Group group = constructGroup();
        group.participate(PARTICIPANT);
        setPastDeadline(group, yesterday);

        assertThatThrownBy(() -> group.validateMemberCanLeave(PARTICIPANT))
            .isInstanceOf(MomoException.class)
            .hasMessage("모집이 마감된 모임입니다.");
    }

    @DisplayName("조기 종료된 모임에는 탈퇴할 수 없다")
    @Test
    void validateLeaveEarlyClosed() {
        Group group = constructGroup();
        group.participate(PARTICIPANT);
        group.closeEarly();

        assertThatThrownBy(() -> group.validateMemberCanLeave(PARTICIPANT))
            .isInstanceOf(MomoException.class)
            .hasMessage("조기종료된 모임입니다.");
    }

    private Group constructGroup() {
        return constructGroupWithSetCapacity(10);
    }

    private Group constructGroupWithSetCapacity(int capacity) {
        Schedules schedules = new Schedules(List.of(이틀후_10시부터_12시까지.newInstance()));
        return new Group(new GroupName("momo 회의"), HOST, Category.STUDY, new Capacity(capacity), 이틀후부터_일주일후까지.getInstance(),
                new Deadline(내일_23시_59분.getInstance()),
                schedules, "", "");
    }

    private Group constructGroupWithSetDeadline(LocalDateTime deadline) {
        Schedules schedules = new Schedules(List.of(이틀후_10시부터_12시까지.newInstance()));
        return new Group(new GroupName("momo 회의"), HOST, Category.STUDY, new Capacity(10), 이틀후부터_일주일후까지.getInstance(),
                new Deadline(deadline), schedules, "", "");
    }

    private Group constructGroupWithSetPastDeadline(LocalDateTime deadline) throws IllegalAccessException {
        Schedules schedules = new Schedules(List.of(이틀후_10시부터_12시까지.newInstance()));
        Group group = new Group(new GroupName("momo 회의"), HOST, Category.STUDY, new Capacity(10), 이틀후부터_일주일후까지.getInstance(),
                new Deadline(내일_23시_59분.getInstance()), schedules, "", "");

        setPastDeadline(group, deadline);

        return group;
    }

    private void setPastDeadline(Group group, LocalDateTime date) throws IllegalAccessException {
        LocalDateTime original = LocalDateTime.of(group.getDuration().getStartDate().minusDays(1), LocalTime.now());
        Deadline deadline = new Deadline(original);
        Calendar calendar = new Calendar(new Schedules(group.getSchedules()), group.getDuration(), deadline);

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

        int deadlineField = 4;
        Class<Group> clazzGroup = Group.class;
        Field[] fieldGroup = clazzGroup.getDeclaredFields();
        fieldGroup[deadlineField].setAccessible(true);
        fieldGroup[deadlineField].set(group, calendar);
    }
}
