package com.woowacourse.momo.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.DUDU_COFFEE_TIME;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_TRAVEL;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class FavoriteRepositoryTest {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    Member member;
    Group studyGroup;
    Group travelGroup;
    Favorite studyGroupFavorite;
    Favorite travelGroupFavorite;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(MOMO.toMember());
        this.member = member;

        studyGroup = groupRepository.save(MOMO_STUDY.toGroup(member));
        travelGroup = groupRepository.save(MOMO_TRAVEL.toGroup(member));

        studyGroupFavorite = new Favorite(studyGroup.getId(), member.getId());
        favoriteRepository.save(studyGroupFavorite);
        travelGroupFavorite = new Favorite(travelGroup.getId(), member.getId());
        favoriteRepository.save(travelGroupFavorite);
    }

    @DisplayName("찜하기 데이터를 저장한다")
    @Test
    void save() {
        Group coffeeGroup = groupRepository.save(DUDU_COFFEE_TIME.toGroup(member));
        Favorite actual = favoriteRepository.save(new Favorite(coffeeGroup.getId(), member.getId()));

        Optional<Favorite> expected = favoriteRepository.findByGroupIdAndMemberId(coffeeGroup.getId(),
                member.getId());

        assertThat(expected).isPresent();
        assertThat(expected.get()).isEqualTo(actual);
    }

    @DisplayName("찜하기 데이터의 유무를 확인한다")
    @Test
    void existsByGroupIdAndMemberId() {
        boolean expected = favoriteRepository.existsByGroupIdAndMemberId(studyGroup.getId(), member.getId());

        assertThat(expected).isTrue();
    }

    @DisplayName("찜하기 데이터를 조회한다")
    @Test
    void findByGroupIdAndMemberId() {
        Optional<Favorite> expected = favoriteRepository.findByGroupIdAndMemberId(studyGroup.getId(), member.getId());

        assertThat(expected).isPresent();
        assertThat(expected.get()).isEqualTo(studyGroupFavorite);
    }

    @DisplayName("회원 id를 통해 모든 찜하기 데이터를 조회한다")
    @Test
    void findAllByMemberId() {
        List<Favorite> expected = favoriteRepository.findAllByMemberId(member.getId());

        assertThat(expected).contains(studyGroupFavorite, travelGroupFavorite);
    }

    @DisplayName("모임 정보를 삭제한다")
    @Test
    void delete() {
        favoriteRepository.delete(studyGroupFavorite);

        Optional<Favorite> expected = favoriteRepository.findByGroupIdAndMemberId(studyGroup.getId(), member.getId());
        assertThat(expected).isEmpty();
    }

    @DisplayName("모임 id를 통해 연관된 모든 찜하기 정보를 삭제한다")
    @Test
    void deleteAllByGroupId() {
        favoriteRepository.deleteAllByGroupId(studyGroup.getId());

        Optional<Favorite> expected = favoriteRepository.findByGroupIdAndMemberId(studyGroup.getId(), member.getId());
        assertThat(expected).isEmpty();
    }

    @DisplayName("회원 id를 통해 연관된 모든 찜하기 정보를 삭제한다")
    @Test
    void deleteAllByMemberId() {
        favoriteRepository.deleteAllByMemberId(member.getId());

        Optional<Favorite> expected = favoriteRepository.findByGroupIdAndMemberId(studyGroup.getId(), member.getId());
        assertThat(expected).isEmpty();
    }
}
