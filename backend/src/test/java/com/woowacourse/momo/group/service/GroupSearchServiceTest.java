package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.group.service.dto.request.GroupSearchRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupSearchServiceTest {

    private final GroupSearchService groupService;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    private Member host;

    @BeforeEach
    void setUp() {
        host = memberRepository.save(MOMO.toMember());
    }

    private Group saveGroup(String name, Category category) {
        return groupRepository.save(MOMO_STUDY.builder()
                .name(name)
                .category(category)
                .toGroup(host));
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findById() {
        Group group = groupRepository.save(MOMO_STUDY.toGroup(host));

        GroupResponse actual = groupService.findGroup(group.getId(), null);
        GroupResponse expected = GroupResponseAssembler.groupResponseWithoutLogin(group);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 모임을 조회할 수 없다")
    @Test
    void findByIdWithNotExistGroupId() {
        assertThatThrownBy(() -> groupService.findGroup(0L, null))
                .isInstanceOf(GroupException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("키워드를 포함하는 이름의 모임을 조회한다")
    @Test
    void findAllByKeyword() {
        saveGroup("모모의 스터디", Category.STUDY);
        saveGroup("구구의 스터디", Category.STUDY);
        saveGroup("모모의 술파티", Category.DRINK);
        saveGroup("두두와의 헬스 클럽", Category.HEALTH);

        GroupSearchRequest request = new GroupSearchRequest();
        request.setKeyword("모모");
        request.setPage(0);

        GroupPageResponse actual = groupService.findGroups(request, null);

        assertThat(actual.getGroups()).hasSize(2);
    }

    @DisplayName("페이지 테스트")
    @TestInstance(value = Lifecycle.PER_CLASS)
    @Nested
    class PageTest {

        private static final int PAGE_SIZE = 12;
        private static final int TWO_PAGE_GROUPS = 8;

        @DisplayName("모임 목록중 두번째 페이지를 조회한다")
        @Test
        void findAllWithPage() {
            List<GroupSummaryResponse> summaries = IntStream.range(0, TWO_PAGE_GROUPS)
                    .mapToObj(i -> saveGroup("모모의 스터디", Category.STUDY))
                    .map(GroupResponseAssembler::groupSummaryResponseWithoutLogin)
                    .sorted(Comparator.comparing(GroupSummaryResponse::getId).reversed())
                    .collect(Collectors.toList());

            for (int i = 0; i < PAGE_SIZE; i++) {
                saveGroup("모모의 스터디", Category.STUDY);
            }

            GroupSearchRequest request = new GroupSearchRequest();
            request.setPage(1);

            GroupPageResponse actual = groupService.findGroups(request, null);

            assertAll(
                    () -> assertThat(actual.isHasNextPage()).isFalse(),
                    () -> assertThat(actual.getGroups()).usingRecursiveFieldByFieldElementComparator()
                            .isEqualTo(summaries)
            );
        }

        @DisplayName("카테고리의 그룹 목록을 조회한다")
        @Test
        void findGroupsByCategory() {
            for (int i = 0; i < PAGE_SIZE + TWO_PAGE_GROUPS; i++) {
                saveGroup("모모의 스터디", Category.STUDY);
                saveGroup("모모의 술파티", Category.DRINK);
            }

            GroupSearchRequest request = new GroupSearchRequest();
            request.setCategory(Category.DRINK.getId());
            request.setPage(1);

            GroupPageResponse actual = groupService.findGroups(request, null);

            assertThat(actual.getGroups()).hasSize(TWO_PAGE_GROUPS);
        }
    }
}
