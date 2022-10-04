package com.woowacourse.momo.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

class FavoriteTest {

    private static final Member MEMBER_MOMO = MOMO.toMember();
    private static final Group STUDY_GROUP = MOMO_STUDY.toGroup(MEMBER_MOMO);
    private static final Group TRAVEL_GROUP = MOMO_TRAVEL.toGroup(MEMBER_MOMO);

    @DisplayName("동일한 모임이면 True를 반환한다")
    @Test
    void isSameMember() {
        Favorite favorite = new Favorite(STUDY_GROUP, MEMBER_MOMO);
        boolean actual = favorite.isSameGroup(STUDY_GROUP);

        assertThat(actual).isTrue();
    }

    @DisplayName("동일한 모임이 아닐 경우 False를 반환한다")
    @Test
    void isNotSameMember() {
        Favorite favorite = new Favorite(STUDY_GROUP, MEMBER_MOMO);
        boolean actual = favorite.isSameGroup(TRAVEL_GROUP);

        assertThat(actual).isFalse();
    }
}
