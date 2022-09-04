package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.service.request.GroupFindRequest;
import com.woowacourse.momo.group.service.response.GroupPageResponse;
import com.woowacourse.momo.group.service.response.GroupResponse;
import com.woowacourse.momo.group.service.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;

@Transactional
@SpringBootTest
class GroupSearchServiceTest {

    private static final int PAGE_SIZE = 12;
    private static final int TWO_PAGE_GROUPS = 8;

    @Autowired
    private GroupSearchService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipantService participantService;

    private Password password;
    private Member savedHost;
    private Member savedMember1;
    private Member savedMember2;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        savedHost = memberRepository.save(new Member(UserId.momo("주최자"), password, "momo"));
        savedMember1 = memberRepository.save(new Member(UserId.momo("사용자1"), password, "momo"));
        savedMember2 = memberRepository.save(new Member(UserId.momo("사용자2"), password, "momo"));
    }

    private Group saveGroup(String name, Category category) {
        return groupRepository.save(MOMO_STUDY.builder()
                .name(name)
                .category(category)
                .toGroup(savedHost));
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findById() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        GroupResponse expected = GroupResponseAssembler.groupResponse(savedGroup);

        GroupResponse actual = groupService.findGroup(savedGroup.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 모임을 조회할 수 없다")
    @Test
    void findByIdWithNotExistGroupId() {
        assertThatThrownBy(() -> groupService.findGroup(0L))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("모임 목록중 두번째 페이지를 조회한다")
    @Test
    void findAllWithPage() {
        List<GroupSummaryResponse> summaries = IntStream.range(0, TWO_PAGE_GROUPS)
                .mapToObj(i -> saveGroup("모모의 스터디", Category.STUDY))
                .map(GroupResponseAssembler::groupSummaryResponse)
                .sorted(Comparator.comparing(GroupSummaryResponse::getId).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < PAGE_SIZE; i++) {
            saveGroup("모모의 스터디", Category.STUDY);
        }

        GroupFindRequest request = new GroupFindRequest();
        request.setPage(1);
        GroupPageResponse actual = groupService.findGroups(request);

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

        GroupFindRequest request = new GroupFindRequest();
        request.setCategory(Category.DRINK.getId());
        request.setPage(1);
        GroupPageResponse actual = groupService.findGroups(request);

        assertThat(actual.getGroups()).hasSize(TWO_PAGE_GROUPS);
    }

    @DisplayName("키워드를 포함하는 이름의 모임을 조회한다")
    @Test
    void findAllByKeyword() {
        saveGroup("모모의 스터디", Category.STUDY);
        saveGroup("구구의 스터디", Category.STUDY);
        saveGroup("모모의 술파티", Category.DRINK);
        saveGroup("두두와의 헬스 클럽", Category.HEALTH);

        GroupFindRequest request = new GroupFindRequest();
        request.setKeyword("모모");
        request.setPage(0);
        GroupPageResponse actual = groupService.findGroups(request);

        assertThat(actual.getGroups()).hasSize(2);
    }
}
