package com.woowacourse.momo.group.domain.favorite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.Member;

class FavoritesTest {

    private static final Member HOST = MOMO.toMember();
    private static final Member MEMBER = DUDU.toMember();
    private static final Group STUDY_GROUP = MOMO_STUDY.toGroup(HOST);

    @DisplayName("모임을 찜한다")
    @Test
    void like() {
        Favorites favorites = new Favorites();
        favorites.like(STUDY_GROUP, MEMBER);

        assertThat(favorites.hasMember(MEMBER)).isTrue();
    }

    @DisplayName("이미 찜한 모임을 찜하면 예외가 발생한다")
    @Test
    void validateMemberNotYetLike() {
        Favorites favorites = new Favorites();
        favorites.like(STUDY_GROUP, MEMBER);

        assertThatThrownBy(() -> favorites.like(STUDY_GROUP, MEMBER))
                .isInstanceOf(GroupException.class)
                .hasMessage("이미 찜한 모임입니다.");
    }

    @DisplayName("모임을 찜하기를 취소한다")
    @Test
    void cancel() {
        Favorites favorites = new Favorites();
        favorites.like(STUDY_GROUP, MEMBER);

        favorites.cancel(MEMBER);
        assertThat(favorites.hasMember(MEMBER)).isFalse();
    }

    @DisplayName("찜하지 않은 모임을 찜하기 취소를 하면 예외가 발생한다")
    @Test
    void validateMemberAlreadyLike() {
        Favorites favorites = new Favorites();

        assertThatThrownBy(() -> favorites.cancel(MEMBER))
                .isInstanceOf(GroupException.class)
                .hasMessage("찜하지 않은 모임입니다.");
    }
}
