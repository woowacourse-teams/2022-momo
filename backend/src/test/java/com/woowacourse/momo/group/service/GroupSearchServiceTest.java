package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.category.domain.Category.STUDY;
import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.GUGU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.이틀후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.일주일후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.favorite.service.FavoriteService;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.dto.request.GroupSearchRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.storage.support.ImageProvider;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupSearchServiceTest {

    private final GroupSearchService groupSearchService;
    private final FavoriteService favoriteService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final ImageProvider imageProvider;

    private Member host;
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;
    private Group group5;
    private Group group6;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(MOMO.toMember());
        group1 = groupRepository.save(constructGroup("모모의 스터디", host, STUDY, 5, 이틀후_23시_59분까지));
        favoriteRepository.save(new Favorite(group1.getId(), host.getId()));
        group2 = groupRepository.save(constructGroup("모모의 술파티", host, Category.DRINK, 15, 내일_23시_59분까지));
        group3 = groupRepository.save(constructGroup("모모의 헬스클럽", host, Category.HEALTH, 1, 일주일후_23시_59분까지));
        favoriteRepository.save(new Favorite(group3.getId(), host.getId()));

        Member anotherHost = memberRepository.save(DUDU.toMember());
        group4 = groupRepository.save(constructGroup("두두의 스터디", anotherHost, STUDY, 6, 내일_23시_59분까지));
        group5 = groupRepository.save(constructGroup("두두의 스터디", anotherHost, STUDY, 10, 이틀후_23시_59분까지));
        group5.participate(host);
        favoriteRepository.save(new Favorite(group5.getId(), host.getId()));

        Group group = constructGroup("두두의 스터디", anotherHost, STUDY, 10, 이틀후_23시_59분까지);
        GroupFixture.setDeadlinePast(group, 1);
        group6 = groupRepository.save(group);
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findById() {
        String imageUrl = imageProvider.generateGroupImageUrl(group1.getCategory().getDefaultImageName(), true);

        GroupResponse actual = groupSearchService.findGroup(group1.getId());
        GroupResponse expected = GroupResponseAssembler.groupResponse(group1, imageUrl);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 모임을 조회할 수 없다")
    @Test
    void findByIdWithNotExistGroupId() {
        assertThatThrownBy(() -> groupSearchService.findGroup(0L))
                .isInstanceOf(GroupException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findGroups() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findGroups(request);

        assertThat(actual.getGroups()).hasSize(6);
    }

    @DisplayName("모임 목록 조회 시 모임의 이미지 정보가 없으면 기본 이미지를 반환한다")
    @Test
    void findGroupsWithoutGroupImage() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setKeyword("모모의 스터디");
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findGroups(request);

        assertThat(actual.getGroups()).hasSize(1);
        assertThat(actual.getGroups().get(0).getImageUrl())
                .isEqualTo("https://image.moyeora.site/group/default/thumbnail_study.jpg");
    }

    @DisplayName("키워드를 포함하는 이름의 모임을 조회한다")
    @Test
    void findAllByKeyword() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setKeyword("모모");
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findGroups(request);

        assertThat(actual.getGroups()).hasSize(3);
    }

    @DisplayName("카테고리의 그룹 목록을 조회한다")
    @Test
    void findGroupsByCategory() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setCategory(Category.STUDY.getId());
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findGroups(request);

        assertThat(actual.getGroups()).hasSize(4);
    }

    @DisplayName("주최한 모임을 조회한다")
    @Test
    void findHostedGroups() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findHostedGroups(request, host.getId());

        assertThat(actual.getGroups()).hasSize(3);
    }

    @DisplayName("참여한 모임 목록을 조회한다")
    @Test
    void findParticipatedGroups() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findParticipatedGroups(request, host.getId());

        assertThat(actual.getGroups()).hasSize(4);
    }

    @DisplayName("찜한 모임을 조회한다")
    @Test
    void findLikedGroups() {
        GroupSearchRequest request = new GroupSearchRequest();
        request.setPage(0);

        GroupPageResponse actual = groupSearchService.findLikedGroups(request, host.getId());

        assertThat(actual.getGroups()).hasSize(3);
    }


    @DisplayName("페이지 테스트")
    @TestInstance(value = Lifecycle.PER_CLASS)
    @Nested
    class PageTest {

        // 기존 6개 + 12개 추가 생성 -> 총 16개 모임
        private static final int PAGE_SIZE = 12;
        private static final int TWO_PAGE_GROUPS = 6;

        @BeforeEach
        void setUp() {
            for (int i = 0; i < PAGE_SIZE; i++) {
                Group group = groupRepository.save(constructGroup("모모의 스터디", host, STUDY, 5, 이틀후_23시_59분까지));
            }
        }

        @DisplayName("모임 목록중 첫번째 페이지를 조회한다")
        @Test
        void findFirstPage() {
            GroupSearchRequest request = new GroupSearchRequest();
            request.setPage(0);

            GroupPageResponse actual = groupSearchService.findGroups(request);

            assertAll(
                    () -> assertThat(actual.isHasNextPage()).isTrue(),
                    () -> assertThat(actual.getGroups()).hasSize(PAGE_SIZE)
            );
        }

        @DisplayName("모임 목록중 두번째 페이지를 조회한다")
        @Test
        void findSecondPage() {
            GroupSearchRequest request = new GroupSearchRequest();
            request.setPage(1);

            GroupPageResponse actual = groupSearchService.findGroups(request);

            assertAll(
                    () -> assertThat(actual.isHasNextPage()).isFalse(),
                    () -> assertThat(actual.getGroups()).hasSize(TWO_PAGE_GROUPS)
            );
        }
    }

    @DisplayName("찜한 모임 페이지 테스트")
    @TestInstance(value = Lifecycle.PER_CLASS)
    @Nested
    class FavoriteGroupPageTest {

        private static final int PAGE_SIZE = 12;
        private static final int TWO_PAGE_GROUPS = 8;

        private Member member;

        @BeforeEach
        void setUp() {
            member = memberRepository.save(GUGU.toMember());
            for (int i = 0; i < PAGE_SIZE + TWO_PAGE_GROUPS; i++) {
                Group group = groupRepository.save(constructGroup("모모의 스터디", host, STUDY, 5, 이틀후_23시_59분까지));
                favoriteService.like(group.getId(), member.getId());
            }
        }

        @DisplayName("찜한 모임 목록중 첫번째 페이지를 조회한다")
        @Test
        void findLikedGroupsFirstPage() {
            GroupSearchRequest request = new GroupSearchRequest();
            request.setPage(0);

            GroupPageResponse actual = groupSearchService.findLikedGroups(request, member.getId());

            assertAll(
                    () -> assertThat(actual.isHasNextPage()).isTrue(),
                    () -> assertThat(actual.getGroups()).hasSize(PAGE_SIZE)
            );
        }

        @DisplayName("찜한 모임 목록중 두번째 페이지를 조회한다")
        @Test
        void findLikedGroupsSecondPage() {
            GroupSearchRequest request = new GroupSearchRequest();
            request.setPage(1);

            GroupPageResponse actual = groupSearchService.findLikedGroups(request, member.getId());

            assertAll(
                    () -> assertThat(actual.isHasNextPage()).isFalse(),
                    () -> assertThat(actual.getGroups()).hasSize(TWO_PAGE_GROUPS)
            );
        }

    }

    private Group constructGroup(String name, Member host, Category category, int capacity, DeadlineFixture deadline) {
        return MOMO_STUDY.builder()
                .name(name)
                .category(category)
                .capacity(capacity)
                .deadline(deadline)
                .duration(일주일후_하루동안)
                .schedules(일주일후_10시부터_12시까지)
                .toGroup(host);
    }
}
