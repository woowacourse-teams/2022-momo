package com.woowacourse.momo.favorite.event;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.service.MemberService;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class MemberDeleteEventListenerTest {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final MemberService memberService;

    private Member momo;
    private Member dudu;
    private Group studyGroup;
    private Group travelGroup;

    @BeforeEach
    void setUp() {
        this.momo = memberRepository.save(MOMO.toMember());
        this.dudu = memberRepository.save(DUDU.toMember());
        this.studyGroup = groupRepository.save(MOMO_STUDY.toGroup(momo));
        this.travelGroup = groupRepository.save(MOMO_TRAVEL.toGroup(momo));

        favoriteRepository.save(new Favorite(studyGroup.getId(), dudu.getId()));
        favoriteRepository.save(new Favorite(travelGroup.getId(), dudu.getId()));
    }

    @DisplayName("회원 삭제시 관련된 찜 데이터를 삭제한다")
    @Test
    void deleteRelatedMemberData() {
        memberService.deleteById(dudu.getId());

        List<Favorite> actual = favoriteRepository.findAllByMemberId(dudu.getId());

        assertThat(actual).isEmpty();
    }
}
