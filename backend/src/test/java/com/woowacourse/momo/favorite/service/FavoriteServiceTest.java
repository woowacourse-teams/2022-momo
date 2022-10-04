package com.woowacourse.momo.favorite.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class FavoriteServiceTest {

    private final FavoriteService favoriteService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final EntityManager entityManager;

    private Group group;
    private Member host;
    private Member user;

    @BeforeEach
    void setUp() {
        this.host = memberRepository.save(MOMO.toMember());
        this.group = groupRepository.save(MOMO_STUDY.toGroup(host));
        this.user = memberRepository.save(DUDU.toMember());
    }

    @DisplayName("모임을 찜한다")
    @Test
    void like() {
        long groupId = group.getId();
        long userId = user.getId();

        synchronize();

        favoriteService.like(groupId, userId);
        synchronize();

        boolean expected = favoriteRepository.existsByGroupIdAndMemberId(groupId, userId);

        assertThat(expected).isTrue();
    }

    @DisplayName("마감된 모임을 찜한다")
    @Test
    void likeClosedGroup() {
        group.closeEarly();
        long groupId = group.getId();
        long userId = user.getId();

        favoriteService.like(groupId, userId);
        synchronize();

        boolean expected = favoriteRepository.existsByGroupIdAndMemberId(groupId, userId);

        assertThat(expected).isTrue();
    }

    @DisplayName("모임의 찜하기를 취소한다")
    @Test
    void cancel() {
        long groupId = group.getId();
        long userId = user.getId();

        favoriteService.like(groupId, userId);
        favoriteService.cancel(groupId, userId);
        synchronize();

        boolean expected = favoriteRepository.existsByGroupIdAndMemberId(groupId, userId);

        assertThat(expected).isFalse();
    }

    @DisplayName("마감된 모임의 찜하기를 취소한다")
    @Test
    void cancelClosedGroup() {
        group.closeEarly();
        long groupId = group.getId();
        long userId = user.getId();

        favoriteService.like(groupId, userId);
        favoriteService.cancel(groupId, userId);
        synchronize();

        Optional<Favorite> foundGroup = favoriteRepository.findByGroupIdAndMemberId(groupId, userId);
        boolean expected = foundGroup.isPresent();

        assertThat(expected).isFalse();
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
