package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DateTimeFixture.어제_23시_59분;
import static com.woowacourse.momo.fixture.DateTimeFixture.이틀후_23시_59분;
import static com.woowacourse.momo.fixture.DateTimeFixture.일주일후_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.ScheduleFixture.이틀후_10시부터_12시까지;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupFindRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

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
        momo = memberRepository.save(new Member("momo", "password", "momo"));
        dudu = memberRepository.save(new Member("dudu", "password", "dudu"));
        group1 = groupRepository.save(constructGroup("모모의 스터디", momo, Category.STUDY, 5, 이틀후_23시_59분.getInstance()));
        group2 = groupRepository.save(constructGroup("모모의 술파티", momo, Category.DRINK, 15, 내일_23시_59분.getInstance()));
        group3 = groupRepository.save(constructGroup("모모의 헬스클럽", momo, Category.HEALTH, 1, 일주일후_23시_59분.getInstance()));
        group4 = groupRepository.save(constructGroup("두두의 스터디", dudu, Category.STUDY, 6, 내일_23시_59분.getInstance()));
        group5 = groupRepository.save(constructGroup("두두의 스터디", dudu, Category.STUDY, 10, 이틀후_23시_59분.getInstance()));
        group5.participate(momo);

        Group group = constructGroup("두두의 스터디", dudu, Category.STUDY, 10, LocalDateTime.now().plusMinutes(1));
        setPastDeadline(group, 어제_23시_59분.getInstance());
        group6 = groupRepository.save(group);

        synchronize();
    }

    @DisplayName("카테고리에 해당하는 모임 목록을 조회한다")
    @Test
    void findAllThatFilterByCategory() {
        Category category = Category.STUDY;
        Specification<Group> specification = GroupSpecification.filterByCategory(category.getId());
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group1, group4, group5, group6));
    }

    @DisplayName("회원이 참여한 모임 목록을 조회한다")
    @Test
    void findAllThatFilterByParticipated() {
        Specification<Group> specification = GroupSpecification.filterByParticipated(momo);
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group1, group2, group3, group5));
    }

    @DisplayName("회원이 주최한 모임 목록을 조회한다")
    @Test
    void findAllThatFilterByHosted() {
        Specification<Group> specification = GroupSpecification.filterByHosted(dudu);
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group4, group5, group6));
    }

    @DisplayName("키워드가 포함된 모임 목록을 조회한다")
    @Test
    void findAllThatContainKeywords() {
        String keyword = "모모";
        Specification<Group> specification = GroupSpecification.containKeyword(keyword);
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group1, group2, group3));
    }

    @DisplayName("모집 완료된 모임을 제외한 목록을 조회한다")
    @Test
    void findAllThatExcludeFinishedRecruitment() {
        Specification<Group> specification = GroupSpecification.excludeFinishedRecruitment(true);
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group1, group2, group4, group5));
    }

    @DisplayName("마감기한이 적은 순으로 목록을 조회한다")
    @Test
    void findAllThatOrderByDeadline() {
        Specification<Group> specification = GroupSpecification.orderByDeadline(true);
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group4, group2, group5, group1, group3));
    }

    @DisplayName("생성된 역순으로 목록을 조회한다")
    @Test
    void findAllThatOrderByIdDesc() {
        Specification<Group> specification = GroupSpecification.orderByDeadline(null);
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group6, group5, group4, group3, group2, group1));
    }

    @DisplayName("키워드가 포함된 목록 중 모집 완료된 모임을 제외한 목록을 조회한다")
    @Test
    void findAllThatContainKeywordsAndExcludeFinishedRecruitment() {
        String keyword = "모모";
        Specification<Group> specification = GroupSpecification.containKeyword(keyword)
                .and(GroupSpecification.excludeFinishedRecruitment(true));
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group1, group2));
    }

    @DisplayName("키워드가 포함된 목록 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findAllThatContainKeywordsOrderByDeadline() {
        String keyword = "모모";
        Specification<Group> specification = GroupSpecification.containKeyword(keyword)
                .and(GroupSpecification.orderByDeadline(true));
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group2, group1, group3));
    }

    @DisplayName("모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findAllThatExcludeFinishedRecruitmentOrderByDeadline() {
        Specification<Group> specification = GroupSpecification.excludeFinishedRecruitment(true)
                .and(GroupSpecification.orderByDeadline(true));
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group4, group2, group5, group1));
    }

    @DisplayName("키워드가 포함되고 모집 마감이 완료된 모임을 제외한 모임 중 마감기한이 적게 남은 순으로 목록을 조회한다")
    @Test
    void findAllThatContainKeywordsAndExcludeFinishedRecruitmentOrderByDeadline() {
        String keyword = "모모";
        Specification<Group> specification = GroupSpecification.containKeyword(keyword)
                .and(GroupSpecification.excludeFinishedRecruitment(true))
                .and(GroupSpecification.orderByDeadline(true));
        List<Group> actual = groupRepository.findAll(specification);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(group2, group1));
    }

    private Group constructGroup(String name, Member host, Category category, int capacity, LocalDateTime deadline) {
        return new Group(name, host, category, capacity, 일주일후_하루동안.getInstance(),
                deadline, List.of(이틀후_10시부터_12시까지.newInstance()), "", "");
    }

    private static void setPastDeadline(Group group, LocalDateTime deadline) throws IllegalAccessException {
        int deadlineField = 8;
        Class<Group> clazz = Group.class;
        Field[] field = clazz.getDeclaredFields();
        field[deadlineField].setAccessible(true);
        field[deadlineField].set(group, deadline);
    }

    private void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
