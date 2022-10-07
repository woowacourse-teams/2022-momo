package com.woowacourse.momo.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

class FavoriteTest {

    private Long memberId;
    private Long studyGroupId;
    private Long travelGroupId;

    @BeforeEach
    void setUp() {
        Member member = MOMO.toMember();
        setMemberId(member, 1L);
        memberId = member.getId();

        Group studyGroup = MOMO_STUDY.toGroup(member);
        setGroupId(studyGroup, 1L);
        studyGroupId = studyGroup.getId();

        Group travelGroup = MOMO_TRAVEL.toGroup(member);
        setGroupId(travelGroup, 2L);
        travelGroupId = travelGroup.getId();
    }

    @DisplayName("동일한 모임이면 True를 반환한다")
    @Test
    void isSameGroup() {
        Favorite favorite = new Favorite(studyGroupId, memberId);
        boolean actual = favorite.isSameGroup(studyGroupId);

        assertThat(actual).isTrue();
    }

    @DisplayName("동일한 모임이 아닐 경우 False를 반환한다")
    @Test
    void isNotSameGroup() {
        Favorite favorite = new Favorite(studyGroupId, memberId);
        boolean actual = favorite.isSameGroup(travelGroupId);

        assertThat(actual).isFalse();
    }

    void setGroupId(Group group, Long groupId) {
        try {
            Field fieldId = Group.class.getDeclaredField("id");
            fieldId.setAccessible(true);
            fieldId.set(group, groupId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("해당하는 필드를 찾을 수 없습니다.");
        }
    }

    void setMemberId(Member member, Long memberId) {
        try {
            Field fieldId = Member.class.getDeclaredField("id");
            fieldId.setAccessible(true);
            fieldId.set(member, memberId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("해당하는 필드를 찾을 수 없습니다.");
        }
    }
}
