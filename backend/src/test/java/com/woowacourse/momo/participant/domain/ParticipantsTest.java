package com.woowacourse.momo.participant.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.domain.group.Capacity;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupName;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;

class ParticipantsTest {

    private static final Password PASSWORD = Password.encrypt("momo123!", new SHA256Encoder());
    private static final Member HOST = new Member(UserId.momo("host"), PASSWORD, "host");
    private static final Capacity CAPACITY = new Capacity(3);
    private static final Group GROUP = constructGroup();
    private static final Member PARTICIPANT = new Member(UserId.momo("participant"), PASSWORD, "participant");
    private static final Member PARTICIPANT2 = new Member(UserId.momo("participant2"), PASSWORD, "participant");
    private static final Member MEMBER = new Member(UserId.momo("member"), PASSWORD, "member");

    @DisplayName("참여자 목록에는 주최자가 포함되어 있다")
    @Test
    void ParticipantsContainHost() {
        Participants participants = new Participants(GROUP, CAPACITY);

        assertThat(participants.contains(HOST)).isTrue();
    }

    @DisplayName("모집이 종료된 모임에 참여할 경우 예외가 발생한다")
    @Test
    void validateGroupIsProceeding() {
        Group group = constructGroup();
        group.closeEarly();
        Participants participants = new Participants(GROUP, CAPACITY);

        assertThatThrownBy(() -> participants.participate(group, PARTICIPANT))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감된 모임에는 참여할 수 없습니다.");
    }

    @DisplayName("주최자가 모임에 참여할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotHost() {
        Participants participants = new Participants(GROUP, CAPACITY);

        assertThatThrownBy(() -> participants.participate(GROUP, HOST))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자는 자신의 모임에 참여할 수 없습니다.");
    }

    @DisplayName("참여자가 다시 참여할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotParticipant() {
        Participants participants = new Participants(GROUP, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThatThrownBy(() -> participants.participate(GROUP, PARTICIPANT))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자는 본인이 참여한 모임에 재참여할 수 없습니다.");
    }

    @DisplayName("수정하려는 최대 인원이 현재 참여자 수보다 적을 경우 예외가 발생한다")
    @Test
    void validateCapacityIsOverNumberOfParticipants() {
        Participants participants = new Participants(GROUP, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThatThrownBy(() -> participants.update(new Capacity(1)))
                .isInstanceOf(MomoException.class)
                .hasMessage("수정하려는 최대 인원이 현재 참가자의 수보다 적습니다.");
    }

    @DisplayName("참여자 정원이 찼을 경우 True 를 반환한다")
    @Test
    void isFullTrue() {
        Participants participants = new Participants(GROUP, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);
        participants.participate(GROUP, PARTICIPANT2);

        assertThat(participants.isFull()).isTrue();
    }

    @DisplayName("참여자 정원이 차지 않았을 경우 False 를 반환한다")
    @Test
    void isFullFalse() {
        Participants participants = new Participants(GROUP, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.isFull()).isFalse();
    }

    @DisplayName("참여자가 있을 경우 True 를 반환한다")
    @Test
    void isExistTrue() {
        Participants participants = new Participants(GROUP, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.isExist()).isTrue();
    }

    @DisplayName("참여자가 없을 경우 True 를 반환한다")
    @Test
    void isExistFalse() {
        Participants participants = new Participants(GROUP, CAPACITY);

        assertThat(participants.isExist()).isFalse();
    }

    @DisplayName("참여자일 경우 True 를 반환한다")
    @Test
    void isParticipantTrue() {
        Participants participants = new Participants(GROUP, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.contains(PARTICIPANT)).isTrue();
    }

    @DisplayName("참여자가 아닐 경우 False 를 반환한다")
    @Test
    void isParticipantFalse() {
        Participants participants = new Participants(GROUP, CAPACITY);

        assertThat(participants.contains(MEMBER)).isFalse();
    }

    private static Group constructGroup() {
        return new Group(new GroupName("모임"), HOST, Category.CAFE, CAPACITY,
                이틀후부터_일주일후까지.getDuration(), new Deadline(내일_23시_59분.getDateTime()),
                new Schedules(List.of(이틀후_10시부터_12시까지.getSchedule())), "", "");
    }
}
