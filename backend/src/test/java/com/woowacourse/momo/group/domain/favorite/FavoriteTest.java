package com.woowacourse.momo.group.domain.favorite;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

class FavoriteTest {

    private static final Member MEMBER_MOMO = MOMO.toMember();
    private static final Member MEMBER_DUDU = DUDU.toMember();
    private static final Group STUDY_GROUP = MOMO_STUDY.toGroup(MEMBER_MOMO);

    @DisplayName("동일한 회원이면 True를 반환한다")
    @Test
    void isSameMember() {
        Favorite favorite = new Favorite(STUDY_GROUP, MEMBER_MOMO);
        boolean actual = favorite.isSameMember(MEMBER_MOMO);

        assertThat(actual).isTrue();
    }

    @DisplayName("동일한 회원이 아닐 경우 False를 반환한다")
    @Test
    void isNotSameMember() {
        Favorite favorite = new Favorite(STUDY_GROUP, MEMBER_MOMO);
        boolean actual = favorite.isSameMember(MEMBER_DUDU);

        assertThat(actual).isFalse();
    }
}
