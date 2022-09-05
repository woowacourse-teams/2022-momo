package com.woowacourse.momo.group.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.GUGU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

class ParticipantTest {

    @DisplayName("정상적으로 생성한다")
    @Test
    void construct() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());
        Member member = DUDU.toMember();
        Participant participant = new Participant(group, member);

        assertAll(
                () -> assertThat(participant.getGroup()).isEqualTo(group),
                () -> assertThat(participant.getMember()).isEqualTo(member)
        );
    }

    @DisplayName("회원이 동일한지 확인한다")
    @ParameterizedTest
    @MethodSource("provideForIsSameMember")
    void isSameMember(Member member, Member target, boolean expected) {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());
        Participant participant = new Participant(group, member);

        assertThat(participant.isSameMember(target)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsSameMember() {
        return Stream.of(
                Arguments.of(DUDU.toMember(), DUDU.toMember(), true),
                Arguments.of(DUDU.toMember(), GUGU.toMember(), false)
        );
    }
}
