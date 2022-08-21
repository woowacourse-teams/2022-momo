package com.woowacourse.momo.participant.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.ScheduleFixture.이틀후_10시부터_12시까지;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.Password;

class ParticipantsTest {

    private static final Password PASSWORD = Password.encrypt("momo123!", new SHA256Encoder());
    private static final Member HOST = new Member("host", PASSWORD, "host");
    private static final Group GROUP = constructGroup();
    private static final Member PARTICIPANT = new Member("participant", PASSWORD, "participant");
    private static final Member MEMBER = new Member("member", PASSWORD, "member");

    @DisplayName("참여자 목록에는 주최자가 포함되어 있다")
    @Test
    void ParticipantsContainHost() {
        Participants participants = new Participants(GROUP, HOST);

        assertThat(participants.isParticipant(HOST)).isTrue();
    }

    @DisplayName("모집이 종료된 모임에 참여할 경우 예외가 발생한다")
    @Test
    void validateGroupIsProceeding() {
        Group group = constructGroup();
        group.closeEarly(HOST);
        Participants participants = new Participants(group, HOST);

        assertThatThrownBy(() -> participants.participate(group, PARTICIPANT))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감된 모임에는 참여할 수 없습니다.");
    }

    @DisplayName("주최자가 모임에 참여할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotHost() {
        Participants participants = new Participants(GROUP, HOST);

        assertThatThrownBy(() -> participants.participate(GROUP, HOST))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자는 자신의 모임에 참여할 수 없습니다.");
    }

    @DisplayName("참여자가 다시 참여할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotParticipant() {
        Participants participants = new Participants(GROUP, HOST);
        participants.participate(GROUP, PARTICIPANT);

        assertThatThrownBy(() -> participants.participate(GROUP, PARTICIPANT))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자는 본인이 참여한 모임에 재참여할 수 없습니다.");
    }

    @DisplayName("참여자 목록의 수를 반환한다")
    @Test
    void size() {
        Participants participants = new Participants(GROUP, HOST);

        assertThat(participants.size()).isEqualTo(1);
    }

    @DisplayName("참여자가 있을 경우 True 를 반환한다")
    @Test
    void isExistTrue() {
        Participants participants = new Participants(GROUP, HOST);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.isExist()).isTrue();
    }

    @DisplayName("참여자가 없을 경우 True 를 반환한다")
    @Test
    void isExistFalse() {
        Participants participants = new Participants(GROUP, HOST);

        assertThat(participants.isExist()).isFalse();
    }

    @DisplayName("참여자일 경우 True 를 반환한다")
    @Test
    void isParticipantTrue() {
        Participants participants = new Participants(GROUP, HOST);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.isParticipant(PARTICIPANT)).isTrue();
    }

    @DisplayName("참여자가 아닐 경우 False 를 반환한다")
    @Test
    void isParticipantFalse() {
        Participants participants = new Participants(GROUP, HOST);

        assertThat(participants.isParticipant(MEMBER)).isFalse();
    }

    private static Group constructGroup() {
        return new Group("모임", HOST, Category.CAFE, 3,
                이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(), List.of(이틀후_10시부터_12시까지.newInstance()),
                "", "");
    }
}
