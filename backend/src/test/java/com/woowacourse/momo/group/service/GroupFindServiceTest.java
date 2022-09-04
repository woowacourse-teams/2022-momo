package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.이틀후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.일주일후_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.calendar.DeadlineFixture;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.service.request.GroupFindRequest;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;

@Transactional
@SpringBootTest
class GroupFindServiceTest {

    @Autowired
    private GroupFindService groupFindService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Password password;
    private Member momo;
    private Member dudu;
    private Group group1;
    private Group group2;
    private Group group3;
    private Group group4;
    private Group group5;
    private Group group6;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        momo = memberRepository.save(new Member(UserId.momo("momo"), password, "momo"));
        dudu = memberRepository.save(new Member(UserId.momo("dudu"), password, "dudu"));
        group1 = groupRepository.save(constructGroup("모모의 스터디", momo, Category.STUDY, 5, 이틀후_23시_59분까지));
        group2 = groupRepository.save(constructGroup("모모의 술파티", momo, Category.DRINK, 15, 내일_23시_59분까지));
        group3 = groupRepository.save(constructGroup("모모의 헬스클럽", momo, Category.HEALTH, 1, 일주일후_23시_59분까지));
        group4 = groupRepository.save(constructGroup("두두의 스터디", dudu, Category.STUDY, 6, 내일_23시_59분까지));
        group5 = groupRepository.save(constructGroup("두두의 스터디", dudu, Category.STUDY, 10, 이틀후_23시_59분까지));
        group5.participate(momo);

        Group group = constructGroup("두두의 스터디", dudu, Category.STUDY, 10, 이틀후_23시_59분까지);
        GroupFixture.setDeadlinePast(group, 1);
        group6 = groupRepository.save(group);
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findGroup() {
        Group actual = groupFindService.findGroup(group1.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(group1);
    }

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findGroups() {
        GroupFindRequest request = new GroupFindRequest();
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group6, group5, group4, group3, group2, group1));
    }

    @DisplayName("참여한 모임 목록을 조회한다")
    @Test
    void findParticipatedGroups() {
        GroupFindRequest request = new GroupFindRequest();
        List<Group> actual = groupFindService.findParticipatedGroups(request, momo).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group5, group3, group2, group1));
    }

    @DisplayName("주최한 모임을 조회한다")
    @Test
    void findHostedGroups() {
        GroupFindRequest request = new GroupFindRequest();
        List<Group> actual = groupFindService.findHostedGroups(request, momo).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group3, group2, group1));
    }

    @DisplayName("카테고리에 해당하는 모임 목록을 조회한다")
    @Test
    void findGroupThatFilterByCategory() {
        GroupFindRequest request = new GroupFindRequest();
        request.setCategory(Category.STUDY.getId());
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group6, group5, group4, group1));
    }

    @DisplayName("키워드가 포함된 모임 목록을 조회한다")
    @Test
    void findGroupThatContainKeywords() {
        String keyword = "모모";
        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword(keyword);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group3, group2, group1));
    }

    @DisplayName("모집 완료된 모임을 제외한 목록을 조회한다")
    @Test
    void findGroupThatExcludeFinishedRecruitment() {
        GroupFindRequest request = new GroupFindRequest();
        request.setExcludeFinished(true);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group5, group4, group2, group1));
    }

    @DisplayName("마감기한이 적은 순으로 목록을 조회한다")
    @Test
    void findGroupThatOrderByDeadline() {
        GroupFindRequest request = new GroupFindRequest();
        request.setOrderByDeadline(true);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group4, group2, group5, group1, group3));
    }

    @DisplayName("키워드가 포함된 목록 중 모집 완료된 모임을 제외한 목록을 조회한다")
    @Test
    void findGroupThatContainKeywordsAndExcludeFinishedRecruitment() {
        String keyword = "모모";
        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword(keyword);
        request.setExcludeFinished(true);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group2, group1));
    }

    @DisplayName("키워드가 포함된 목록 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findGroupThatContainKeywordsOrderByDeadline() {
        String keyword = "모모";
        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword(keyword);
        request.setOrderByDeadline(true);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group2, group1, group3));
    }

    @DisplayName("모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findGroupThatExcludeFinishedRecruitmentOrderByDeadline() {
        GroupFindRequest request = new GroupFindRequest();
        request.setExcludeFinished(true);
        request.setOrderByDeadline(true);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group4, group2, group5, group1));
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findGroupThatContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        String keyword = "모모";
        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword(keyword);
        request.setExcludeFinished(true);
        request.setOrderByDeadline(true);
        List<Group> actual = groupFindService.findGroups(request).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group2, group1));
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 참여한 모임 목록을 조회한다")
    @Test
    void findParticipatedGroupsAndContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        String keyword = "두두";
        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword(keyword);
        request.setExcludeFinished(true);
        request.setOrderByDeadline(true);
        List<Group> actual = groupFindService.findParticipatedGroups(request, momo).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group5));
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 주최한 모임을 조회한다")
    @Test
    void findHostedGroupsAndContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        String keyword = "모모";
        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword(keyword);
        request.setExcludeFinished(true);
        request.setOrderByDeadline(true);
        List<Group> actual = groupFindService.findHostedGroups(request, momo).getContent();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group2, group1));
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
