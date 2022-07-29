package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.DateTimeFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.schedule.Schedule;
import com.woowacourse.momo.member.domain.Member;

class GroupTest {

    private final Member host = new Member("주최자", "password", "momo");

    @DisplayName("유효하지 않은 모임 정원 값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100})
    void validateOutOfCapacityRange(int capacity) {
        assertThatThrownBy(() -> constructGroupWithSetCapacity(capacity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모임 정원은 1명 이상 99명 이하여야 합니다.");
    }

    @DisplayName("유효한 모임 정원 값이면 예외가 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = {1, 99})
    void validateInRangeOfCapacity(int capacity) {
        assertDoesNotThrow(() -> constructGroupWithSetCapacity(capacity));
    }

    @DisplayName("회원이 모임의 주최자일 경우 True 를 반환한다")
    @Test
    void isSameHost() {
        Group group = constructGroup();
        boolean actual = group.isSameHost(host);

        assertThat(actual).isTrue();
    }

    @DisplayName("회원이 모임의 주최자가 아닌 경우 False 를 반환한다")
    @Test
    void isNotSameHost() {
        Group group = constructGroup();
        Member member = new Member("주최자 아님", "password", "momo");
        boolean actual = group.isSameHost(member);

        assertThat(actual).isFalse();
    }

    @DisplayName("모임에 참여한다")
    @Test
    void participate() {
        Group group = constructGroup();
        Member member = new Member("momo@woowa.com", "qwer123!@#", "모모");
        group.participate(member);

        assertThat(group.getParticipants()).hasSize(2);
    }

    @DisplayName("이미 참여한 모임에 참가할 경우 예외가 발생한다")
    @Test
    void validateReParticipant() {
        Group group = constructGroup();
        Member member = new Member("momo@woowa.com", "qwer123!@#", "모모");
        group.participate(member);
        assertThatThrownBy(() -> group.participate(member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 참여한 모임입니다.");
    }

    @DisplayName("정원이 가득찬 모임에 참가할 경우 예외가 발생한다")
    @Test
    void validateOverCapacity() {
        int capacity = 2;
        Group group = constructGroupWithSetCapacity(capacity);
        Member member1 = new Member("momo@woowa.com", "qwer123!@#", "모모");
        group.participate(member1);

        Member member2 = new Member("dudu@woowa.com", "qwer123!@#", "두두");
        assertThatThrownBy(() -> group.participate(member2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정원이 가득 찼습니다.");
    }

    @DisplayName("모임에 참여한 회원을 반환한다")
    @Test
    void getParticipants() {
        Group group = constructGroup();
        Member member = new Member("momo@woowa.com", "qwer123!@#", "모모");
        group.participate(member);

        List<Member> participants = group.getParticipants();
        assertThat(participants).contains(host, member);
    }

    @DisplayName("현재 참여 인원이 정원을 초과하면 모집이 종료된다.")
    @Test
    void isFinishedRecruitmentWithOverCapacity() {
        int capacity = 2;
        Group group = constructGroupWithSetCapacity(capacity);
        Member member = new Member("momo@woowa.com", "qwer123!@#", "모모");
        group.participate(member);

        boolean actual = group.isFinishedRecruitment();
        assertThat(actual).isTrue();
    }

    @DisplayName("모집 마감시간이 지나면 모집이 종료된다")
    @Test
    void isFinishedRecruitmentWithPassedDeadline() {
        LocalDateTime deadline = LocalDateTime.now().minusMinutes(1);
        Group group = constructGroupWithSetDeadline(deadline);

        boolean actual = group.isFinishedRecruitment();
        assertThat(actual).isTrue();
    }

    private Group constructGroup() {
        return constructGroupWithSetCapacity(10);
    }

    private Group constructGroupWithSetCapacity(int capacity) {
        List<Schedule> schedules = List.of(_7월_1일_10시부터_12시까지.newInstance());
        return new Group("momo 회의", host, Category.STUDY, capacity, _7월_1일부터_2일까지.getInstance(),
                _6월_30일_23시_59분.getInstance(),
                schedules, "", "");
    }

    private Group constructGroupWithSetDeadline(LocalDateTime deadline) {
        List<Schedule> schedules = List.of(_7월_1일_10시부터_12시까지.newInstance());
        return new Group("momo 회의", host, Category.STUDY, 10, _7월_1일부터_2일까지.getInstance(),
                deadline, schedules, "", "");
    }
}
