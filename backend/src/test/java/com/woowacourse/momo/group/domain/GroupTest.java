package com.woowacourse.momo.group.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.Member;

class GroupTest {

    private static final Member HOST = MOMO.toMember();

    @DisplayName("조기마감된 모임에 대한 검증 테스트")
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    class EarlyClosedGroupTest {

        private Group group;

        @BeforeEach
        void setUp() {
            group = MOMO_STUDY.toGroup(MOMO.toMember());
            group.closeEarly();
        }

        @DisplayName("조기마감된 모임은 더이상 수정할 수 없습니다")
        @Test
        void cannotUpdateGroupByClosedEarly() {
            assertThatThrownBy(() -> update(group, MOMO_TRAVEL))
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 조기 마감되어 있습니다.");
        }

        @DisplayName("조기마감된 모임은 더이상 삭제할 수 없습니다")
        @Test
        void cannotDeleteGroupByClosedEarly() {
            assertThatThrownBy(group::validateGroupIsProceeding)
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 조기 마감되어 있습니다.");
        }

        @DisplayName("이미 조기마감된 모임은 다시 조기마감할 수 없습니다")
        @Test
        void cannotCloseGroupByAlreadyClosed() {
            assertThatThrownBy(group::closeEarly)
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 조기 마감되어 있습니다.");
        }

        @DisplayName("이미 조기마감된 모임에는 참여할 수 없다")
        @Test
        void cannotParticipateByAlreadyClosed() {
            Member participant = DUDU.toMember();

            assertThatThrownBy(() -> group.participate(participant))
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 조기 마감되어 있습니다.");
        }

        @DisplayName("이미 조기마감된 모임을 탈퇴한다")
        @Test
        void cannotLeaveByAlreadyClosed() {
            Member participant = DUDU.toMember();

            Group group = MOMO_STUDY.toGroup(MOMO.toMember());
            group.participate(participant);
            group.closeEarly();

            assertThatThrownBy(() -> group.remove(participant))
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 조기 마감되어 있습니다.");
        }
    }

    @DisplayName("마감기한이 지나버린 모임에 대한 검증 테스트")
    @Nested
    class DeadlinePassedGroupTest {

        private Group group;

        @BeforeEach
        void setUp() {
            group = MOMO_STUDY.toGroup(MOMO.toMember());
            GroupFixture.setDeadlinePast(group, 1);
        }

        @DisplayName("마감기한이 지나버린 모임은 더이상 수정할 수 없습니다")
        @Test
        void cannotUpdateGroupByDeadlinePassed() {
            assertThatThrownBy(() -> update(group, MOMO_TRAVEL))
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 마감기한이 지났습니다.");
        }

        @DisplayName("마감기한이 지나버린 모임은 더이상 삭제할 수 없습니다")
        @Test
        void cannotDeleteGroupByDeadlinePassed() {
            assertThatThrownBy(group::validateGroupIsProceeding)
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 마감기한이 지났습니다.");
        }

        @DisplayName("마감기한이 지나버린 모임은 조기마감한다")
        @Test
        void cannotCloseGroupByDeadlinePassed() {
            assertThatThrownBy(group::closeEarly)
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 마감기한이 지났습니다.");
        }

        @DisplayName("마감기한이 지나버린 모임에는 참여할 수 없다")
        @Test
        void cannotParticipateByDeadlinePassed() {
            Member participant = DUDU.toMember();

            assertThatThrownBy(() -> group.participate(participant))
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 마감기한이 지났습니다.");
        }

        @DisplayName("마감기한이 지나버린 모임을 탈퇴한다")
        @Test
        void cannotLeaveByDeadlinePassed() {
            Member participant = DUDU.toMember();

            Group group = MOMO_STUDY.toGroup(MOMO.toMember());
            group.participate(participant);
            GroupFixture.setDeadlinePast(group, 1);

            assertThatThrownBy(() -> group.remove(participant))
                    .isInstanceOf(GroupException.class)
                    .hasMessage("해당 모임은 마감기한이 지났습니다.");
        }
    }

    @DisplayName("참여자가 존재하는 모임에 대한 검증 테스트")
    @Nested
    class ParticipantsExistGroupTest {

        private Group group;

        @BeforeEach
        void setUp() {
            group = MOMO_STUDY.toGroup(MOMO.toMember());
            group.participate(DUDU.toMember());
        }

        @DisplayName("참여자가 존재하는 모임을 수정할 수 있습니다")
        @Test
        void cannotUpdateGroupByExistParticipants() {
            assertDoesNotThrow(() -> update(group, MOMO_TRAVEL));
        }

        @DisplayName("참여자가 존재하는 모임을 삭제할 수 있습니다")
        @Test
        void cannotDeleteGroupByExistParticipants() {
            assertDoesNotThrow(group::validateGroupIsProceeding);
        }
    }

    @DisplayName("모임을 생성한다")
    @Test
    void construct() {
        GroupFixture fixture = MOMO_STUDY;

        Member host = MOMO.toMember();
        Capacity capacity = fixture.getCapacity();
        Calendar calendar = fixture.getCalendar();
        GroupName name = fixture.getName();
        Category category = fixture.getCategory();
        Location location = fixture.getLocationObject();
        Description description = fixture.getDescription();

        Group group = fixture.toGroup(host);

        assertAll(
                () -> assertThat(group.getHost().getUserId()).isEqualTo(host.getUserId()),
                () -> assertThat(group.getCapacity()).isEqualTo(capacity.getValue()),
                () -> assertThat(group.getCalendar()).usingRecursiveComparison()
                        .isEqualTo(calendar),
                () -> assertThat(group.getName()).isEqualTo(name.getValue()),
                () -> assertThat(group.getCategory()).isEqualTo(category),
                () -> assertThat(group.getLocation()).isEqualTo(location),
                () -> assertThat(group.getDescription()).isEqualTo(description)
        );
    }

    @DisplayName("모임을 수정한다")
    @Test
    void update() {
        Member host = MOMO.toMember();
        Group group = MOMO_STUDY.toGroup(host);

        GroupFixture fixture = MOMO_TRAVEL;
        Capacity capacity = fixture.getCapacity();
        Calendar calendar = fixture.getCalendar();
        GroupName name = fixture.getName();
        Category category = fixture.getCategory();
        Location location = fixture.getLocationObject();
        Description description = fixture.getDescription();

        group.update(capacity, calendar, name, category, location, description);

        assertAll(
                () -> assertThat(group.getHost().getUserId()).isEqualTo(host.getUserId()),
                () -> assertThat(group.getCapacity()).isEqualTo(capacity.getValue()),
                () -> assertThat(group.getCalendar()).usingRecursiveComparison()
                        .isEqualTo(calendar),
                () -> assertThat(group.getName()).isEqualTo(name.getValue()),
                () -> assertThat(group.getCategory()).isEqualTo(category),
                () -> assertThat(group.getDescription()).isEqualTo(description)
        );
    }

    private void update(Group group, GroupFixture fixture) {
        Capacity capacity = fixture.getCapacity();
        Calendar calendar = fixture.getCalendar();
        GroupName name = fixture.getName();
        Category category = fixture.getCategory();
        Location location = fixture.getLocationObject();
        Description description = fixture.getDescription();

        group.update(capacity, calendar, name, category, location, description);
    }

    @DisplayName("모임 모집을 조기마감한다")
    @Test
    void closeEarly() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());
        group.closeEarly();

        assertThat(group.isClosedEarly()).isTrue();
    }

    @DisplayName("모임에 참여한다")
    @Test
    void participate() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());

        Member participant = DUDU.toMember();
        group.participate(participant);

        assertThat(group.getParticipants()).usingRecursiveComparison()
                .isEqualTo(List.of(HOST, participant));
    }

    @DisplayName("모임을 탈퇴한다")
    @Test
    void remove() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());

        Member participant = DUDU.toMember();
        group.participate(participant);
        group.remove(participant);

        assertThat(group.getParticipants()).usingRecursiveComparison()
                .isEqualTo(List.of(HOST));
    }

    @DisplayName("모임의 주최자와 일치하는지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsHostArguments")
    void isHost(Member member, boolean expected) {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());

        assertThat(group.isHost(member)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsHostArguments() {
        return Stream.of(
                Arguments.of(HOST, true),
                Arguments.of(DUDU.toMember(), false)
        );
    }

    @DisplayName("마감되지 않은 모임에 대해, 모집이 마감되었는지 확인한다")
    @Test
    void isFinishedRecruitment() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());

        assertThat(group.isFinishedRecruitment()).isFalse();
    }

    @DisplayName("조기마감된 모임에 대해, 모집이 마감되었는지 확인한다")
    @Test
    void isFinishedRecruitmentWhenClosedEarly() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());
        group.closeEarly();

        assertThat(group.isFinishedRecruitment()).isTrue();
    }

    @DisplayName("마감기한이 지나버린 모임에 대해, 모집이 마감되었는지 확인한다")
    @Test
    void isFinishedRecruitmentWhenDeadlinePassed() {
        Group group = MOMO_STUDY.toGroup(MOMO.toMember());
        GroupFixture.setDeadlinePast(group, 1);

        assertThat(group.isFinishedRecruitment()).isTrue();
    }

    @DisplayName("참여자가 가득한 모임에 대해, 모집이 마감되었는지 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"2,false", "3,false"})
    void isFinishedRecruitmentWhenParticipantsFull(int capacity, boolean expected) {
        Group group = MOMO_STUDY.builder()
                .capacity(capacity)
                .toGroup(MOMO.toMember());

        group.participate(DUDU.toMember());

        assertThat(group.isFinishedRecruitment()).isEqualTo(expected);
    }
}
