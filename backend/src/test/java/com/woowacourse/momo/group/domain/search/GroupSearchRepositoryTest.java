package com.woowacourse.momo.group.domain.search;

import static org.assertj.core.api.Assertions.assertThat;

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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.favorite.domain.Favorite;
import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.domain.search.dto.GroupSummaryRepositoryResponse;
import com.woowacourse.momo.group.service.dto.request.GroupSearchRequest;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class GroupSearchRepositoryTest {

    private final GroupRepository groupRepository;
    private final GroupSearchRepository groupSearchRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final GroupImageRepository groupImageRepository;

    private Member host;
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;
    private Group group5;
    private Group group6;

    private static final Pageable pageable = PageRequest.of(0, 12);

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

        Member anotherHost2 = memberRepository.save(GUGU.toMember());
        group5.participate(anotherHost2);
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

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findGroups() {
        GroupSearchRequest request = new GroupSearchRequest();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group6, group5, group4, group3, group2, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("참여한 모임 목록을 조회한다")
    @Test
    void findParticipatedGroups() {
        GroupSearchRequest request = new GroupSearchRequest();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findParticipatedGroups(
                request.toFindCondition(), host.getId(), pageable).getContent();

        List<Group> expected = List.of(group5, group3, group2, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("주최한 모임을 조회한다")
    @Test
    void findHostedGroups() {
        GroupSearchRequest request = new GroupSearchRequest();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findHostedGroups(request.toFindCondition(),
                host.getId(), pageable).getContent();

        List<Group> expected = List.of(group3, group2, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("찜한 모임을 조회한다")
    @Test
    void findLikedGroups() {
        GroupSearchRequest request = new GroupSearchRequest();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findLikedGroups(request.toFindCondition(),
                host.getId(), pageable).getContent();

        List<Group> expected = List.of(group5, group3, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("카테고리에 해당하는 모임 목록을 조회한다")
    @Test
    void findGroupThatFilterByCategory() {
        GroupSearchRequest request = new FindRequestBuilder()
                .category(STUDY)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group6, group5, group4, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("키워드가 포함된 모임 목록을 조회한다")
    @Test
    void findGroupThatContainKeywords() {
        GroupSearchRequest request = new FindRequestBuilder()
                .keyword("모모")
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group3, group2, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("모집 완료된 모임을 제외한 목록을 조회한다")
    @Test
    void findGroupThatExcludeFinishedRecruitment() {
        GroupSearchRequest request = new FindRequestBuilder()
                .excludeFinished(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group5, group4, group3, group2, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("마감기한이 적은 순으로 목록을 조회한다")
    @Test
    void findGroupThatOrderByDeadline() {
        GroupSearchRequest request = new FindRequestBuilder()
                .orderByDeadline(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group4, group2, group5, group1, group3);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("키워드가 포함된 목록 중 모집 완료된 모임을 제외한 목록을 조회한다")
    @Test
    void findGroupThatContainKeywordsAndExcludeFinishedRecruitment() {
        GroupSearchRequest request = new FindRequestBuilder()
                .keyword("모모")
                .excludeFinished(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group3, group2, group1);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("키워드가 포함된 목록 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findGroupThatContainKeywordsOrderByDeadline() {
        GroupSearchRequest request = new FindRequestBuilder()
                .keyword("모모")
                .orderByDeadline(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group2, group1, group3);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findGroupThatExcludeFinishedRecruitmentOrderByDeadline() {
        GroupSearchRequest request = new FindRequestBuilder()
                .excludeFinished(true)
                .orderByDeadline(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group4, group2, group5, group1, group3);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findGroupThatContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        GroupSearchRequest request = new FindRequestBuilder()
                .keyword("모모")
                .excludeFinished(true)
                .orderByDeadline(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findGroups(request.toFindCondition(),
                pageable).getContent();

        List<Group> expected = List.of(group2, group1, group3);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 참여한 모임 목록을 조회한다")
    @Test
    void findParticipatedGroupsAndContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        GroupSearchRequest request = new FindRequestBuilder()
                .keyword("두두")
                .excludeFinished(true)
                .orderByDeadline(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findParticipatedGroups(
                        request.toFindCondition(), host.getId(), pageable)
                .getContent();

        List<Group> expected = List.of(group5);
        assertThatGroupsMatch(actual, expected);
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 주최한 모임을 조회한다")
    @Test
    void findHostedGroupsAndContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        GroupSearchRequest request = new FindRequestBuilder()
                .keyword("모모")
                .excludeFinished(true)
                .orderByDeadline(true)
                .build();

        List<GroupSummaryRepositoryResponse> actual = groupSearchRepository.findHostedGroups(request.toFindCondition(),
                        host.getId(), pageable)
                .getContent();

        List<Group> expected = List.of(group2, group1, group3);
        assertThatGroupsMatch(actual, expected);
    }

    void assertThatGroupsMatch(List<GroupSummaryRepositoryResponse> actuals, List<Group> groups) {
        int expectedSize = groups.size();
        assertThat(actuals).hasSize(expectedSize);

        for (int i = 0; i < expectedSize; i++) {
            GroupSummaryRepositoryResponse actual = actuals.get(i);
            Group expected = groups.get(i);

            assertThat(actual.getGroupId()).isEqualTo(expected.getId());
            assertThat(actual.getGroupName()).isEqualTo(expected.getName());
            assertThat(actual.getHostId()).isEqualTo(expected.getHost().getId());
            assertThat(actual.getHostName()).isEqualTo(expected.getHost().getUserName());
            assertThat(actual.getCategory()).isEqualTo(expected.getCategory());
            assertThat(actual.getNumOfParticipant()).isEqualTo(expected.getParticipants().size());
            assertThat(actual.isClosedEarly()).isEqualTo(expected.isClosedEarly());
            assertThat(actual.getDeadline()).isEqualTo(expected.getDeadline());
            assertThat(actual.getImageName()).isEqualTo(
                    groupImageRepository.findByGroupId(expected.getId())
                            .map(GroupImage::getImageName)
                            .orElse(expected.getCategory().getDefaultImageName())
            );
        }
    }

    private static class FindRequestBuilder {

        private int page;
        private Long category;
        private String keyword;
        private boolean excludeFinished;
        private boolean orderByDeadline;

        public FindRequestBuilder() {
            this.page = 0;
            this.excludeFinished = false;
            this.orderByDeadline = false;
        }

        public FindRequestBuilder category(long category) {
            this.category = category;
            return this;
        }

        public FindRequestBuilder category(Category category) {
            return category(category.getId());
        }

        public FindRequestBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public FindRequestBuilder excludeFinished(boolean value) {
            this.excludeFinished = value;
            return this;
        }

        public FindRequestBuilder orderByDeadline(boolean value) {
            this.orderByDeadline = value;
            return this;
        }

        public GroupSearchRequest build() {
            GroupSearchRequest request = new GroupSearchRequest();
            request.setPage(page);
            request.setCategory(category);
            request.setKeyword(keyword);
            request.setExcludeFinished(excludeFinished);
            request.setOrderByDeadline(orderByDeadline);
            return request;
        }
    }
}
