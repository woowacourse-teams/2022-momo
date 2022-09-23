package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class LikeServiceTest {

    private final LikeService likeService;
    private final GroupFindService groupFindService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
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

        likeService.like(groupId, userId);
        synchronize();

        Group findGroup = groupFindService.findGroup(groupId);
        boolean expected = findGroup.isMemberLiked(user);

        assertThat(expected).isTrue();
    }

    @DisplayName("모임을 찜하기를 취소한다")
    @Test
    void cancel() {
        long groupId = group.getId();
        long userId = user.getId();

        likeService.like(groupId, userId);
        likeService.cancel(groupId, userId);
        synchronize();

        Group findGroup = groupFindService.findGroup(groupId);
        boolean expected = findGroup.isMemberLiked(user);

        assertThat(expected).isFalse();
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
