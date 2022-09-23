package com.woowacourse.momo.group.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.Member;

class ParticipantsTest {

    private static final Member HOST = MOMO.toMember();
    private static final Member PARTICIPANT = DUDU.toMember();
    private static final Group GROUP = MOMO_STUDY.toGroup(HOST);
    private static final Capacity CAPACITY = new Capacity(10);

    @DisplayName("정상적으로 생성한다")
    @Test
    void construct() {
        Participants participants = new Participants(HOST, CAPACITY);

        assertAll(
                () -> assertThat(participants.getHost()).isEqualTo(HOST),
                () -> assertThat(participants.getParticipants()).containsExactly(HOST),
                () -> assertThat(participants.getCapacity()).isEqualTo(CAPACITY)
        );
    }

    @DisplayName("참여한다")
    @Test
    void participate() {
        Participants participants = new Participants(HOST, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.getParticipants()).containsExactly(HOST, PARTICIPANT);
    }

    @DisplayName("주최자가 모임에 참여할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotHostWhenParticipate() {
        Participants participants = new Participants(HOST, CAPACITY);

        assertThatThrownBy(() -> participants.participate(GROUP, HOST))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자입니다.");
    }

    @DisplayName("참여자가 다시 참여할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotParticipatedWhenParticipate() {
        Participants participants = new Participants(HOST, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThatThrownBy(() -> participants.participate(GROUP, PARTICIPANT))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 참여자입니다.");
    }

    @DisplayName("참여인원이 가득찬 상태에서 회원이 참여할 경우 예외가 발생한다")
    @Test
    void validateParticipantsNotYetFullWhenParticipate() {
        Participants participants = new Participants(HOST, new Capacity(1));

        assertThatThrownBy(() -> participants.participate(GROUP, PARTICIPANT))
                .isInstanceOf(GroupException.class)
                .hasMessage("참여인원이 가득 찼습니다.");
    }

    @DisplayName("탈퇴한다")
    @Test
    void remove() {
        Participants participants = new Participants(HOST, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);
        participants.remove(PARTICIPANT);

        assertThat(participants.getParticipants()).containsExactly(HOST);
    }

    @DisplayName("주최자가 모임을 탈퇴할 경우 예외가 발생한다")
    @Test
    void validateMemberIsNotHostWhenLeave() {
        Participants participants = new Participants(HOST, CAPACITY);

        assertThatThrownBy(() -> participants.remove(HOST))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 주최자입니다.");
    }

    @DisplayName("참여자가 아닌 회원이 탈퇴할 경우 예외가 발생한다")
    @Test
    void validateMemberParticipatedWhenLeave() {
        Participants participants = new Participants(HOST, CAPACITY);

        assertThatThrownBy(() -> participants.remove(PARTICIPANT))
                .isInstanceOf(GroupException.class)
                .hasMessage("해당 모임의 참여자가 아닙니다.");
    }

    @DisplayName("참여 가능 인원수를 수정한다")
    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7})
    void updateCapacity(int capacity) {
        Participants participants = new Participants(HOST, new Capacity(10));

        Capacity expected = new Capacity(capacity);
        participants.updateCapacity(expected);

        assertThat(participants.getCapacity()).isEqualTo(expected);
    }

    @DisplayName("수정하려는 참여 가능 인원수가 현재 참여자 수보다 적을 경우 예외가 발생한다")
    @Test
    void validateCapacityIsOverNumberOfParticipants() {
        Participants participants = new Participants(HOST, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        int participantsSize = participants.getParticipants().size();
        Capacity lessCapacity = new Capacity(participantsSize - 1);

        assertThatThrownBy(() -> participants.updateCapacity(lessCapacity))
                .isInstanceOf(GroupException.class)
                .hasMessage("참가인원제한은 현재 참가자의 인원수보다 적을 수 없습니다.");
    }

    @DisplayName("참여자가 한명이라도 존재하는지 확인한다")
    @Test
    void isNotEmpty() {
        Participants participants = new Participants(HOST, CAPACITY);
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.isNotEmpty()).isTrue();
    }

    @DisplayName("참여자가 한명이라도 존재하는지 확인한다")
    @Test
    void isEmpty() {
        Participants participants = new Participants(HOST, CAPACITY);

        assertThat(participants.isNotEmpty()).isFalse();
    }

    @DisplayName("참여인원이 가득찼는지 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"2,true", "3,false"})
    void isFull(int capacity, boolean expected) {
        Participants participants = new Participants(HOST, new Capacity(capacity));
        participants.participate(GROUP, PARTICIPANT);

        assertThat(participants.isFull()).isEqualTo(expected);
    }

    @DisplayName("주최자와 일치하는지 확인한다")
    @Test
    void isHost() {
        Participants participants = new Participants(HOST, CAPACITY);

        assertThat(participants.isHost(HOST)).isTrue();
    }
}
