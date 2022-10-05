package com.woowacourse.momo.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class FavoriteRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    Long memberId;
    Long studyGroupId;
    Long travelGroupId;
    Favorite studyGroupFavorite;
    Favorite travelGroupFavorite;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MOMO.toMember());
        memberId = member.getId();

        studyGroupId = groupRepository.save(MOMO_STUDY.toGroup(member)).getId();
        travelGroupId = groupRepository.save(MOMO_TRAVEL.toGroup(member)).getId();

        studyGroupFavorite = new Favorite(studyGroupId, memberId);
        favoriteRepository.save(studyGroupFavorite);
        travelGroupFavorite = new Favorite(travelGroupId, memberId);
        favoriteRepository.save(travelGroupFavorite);
    }

    @DisplayName("찜하기 데이터의 유무를 확인한다")
    @Test
    void existsByGroupIdAndMemberId() {
        boolean expected = favoriteRepository.existsByGroupIdAndMemberId(studyGroupId, memberId);

        assertThat(expected).isTrue();
    }

    @DisplayName("찜하기 데이터를 조회한다")
    @Test
    void findByGroupIdAndMemberId() {
        Optional<Favorite> expected = favoriteRepository.findByGroupIdAndMemberId(studyGroupId, memberId);

        assertThat(expected).isPresent();
        assertThat(expected.get()).isEqualTo(studyGroupFavorite);
    }

    @DisplayName("회원 Id를 통해 모든 찜하기 데이터를 조회한다")
    @Test
    void findAllByMemberId() {
        List<Favorite> expected = favoriteRepository.findAllByMemberId(memberId);

        assertThat(expected).contains(studyGroupFavorite, travelGroupFavorite);
    }
}
