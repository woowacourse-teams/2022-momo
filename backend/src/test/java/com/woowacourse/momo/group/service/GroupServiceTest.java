package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture.이틀후부터_일주일후까지;
import static com.woowacourse.momo.fixture.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.TimeFixture._12시_00분;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.globalException.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@SpringBootTest
class GroupServiceTest {

    private static final DurationRequest DURATION_REQUEST = new DurationRequest(이틀후.getInstance(),
            이틀후.getInstance());
    private static final List<ScheduleRequest> SCHEDULE_REQUESTS = List.of(
            new ScheduleRequest(이틀후.getInstance(), _10시_00분.getInstance(), _12시_00분.getInstance()));
    private static final int PAGE_SIZE = 12;
    private static final int TWO_PAGE_GROUPS = 8;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(new Member("회원", "password", "momo"));
    }

    private Group saveGroup() {
        return groupRepository.save(new Group("모모의 스터디", savedMember, Category.STUDY, 2,
                이틀후부터_일주일후까지.getInstance(), 내일_23시_59분.getInstance(), List.of(이틀후_10시부터_12시까지.newInstance()),
                "", ""));
    }

    @DisplayName("모임을 생성한다")
    @Test
    void create() {
        GroupRequest request = new GroupRequest("모모의 스터디", Category.STUDY.getId(), 10,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getInstance(), "", "");

        groupService.create(savedMember.getId(), request);

        assertThat(groupRepository.findAll()).hasSize(1);
    }

    @DisplayName("유효하지 않은 카테고리로 모임을 생성하면 예외가 발생한다")
    @Test
    void createWithInvalidCategoryId() {
        Long categoryId = 0L;
        GroupRequest request = new GroupRequest("모모의 스터디", categoryId, 10, DURATION_REQUEST,
                SCHEDULE_REQUESTS, 내일_23시_59분.getInstance(), "", "");

        assertThatThrownBy(() -> groupService.create(savedMember.getId(), request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findById() {
        Group savedGroup = saveGroup();
        GroupResponse expected = GroupResponseAssembler.groupResponse(savedGroup);

        GroupResponse actual = groupService.findById(savedGroup.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 모임을 조회할 수 없다")
    @Test
    void findByIdWithNotExistGroupId() {
        assertThatThrownBy(() -> groupService.findById(0L))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findAll() {
        int count = 3;
        List<GroupSummaryResponse> expected = IntStream.range(0, count)
                .mapToObj(i -> saveGroup())
                .map(GroupResponseAssembler::groupSummaryResponse)
                .collect(Collectors.toList());

        List<GroupSummaryResponse> actual = groupService.findAll();

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @DisplayName("모집 마김된 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateFinishedRecruitmentGroup() {
        Group savedGroup = saveGroup();
        Member savedMember2 = memberRepository.save(new Member("회원2", "password", "dudu"));
        savedGroup.participate(savedMember2);
        long groupId = savedGroup.getId();

        GroupUpdateRequest groupRequest = new GroupUpdateRequest(1L, 2,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getInstance(), "", "");

        assertThatThrownBy(() -> groupService.update(savedMember.getId(), groupId, groupRequest))
                .isInstanceOf(MomoException.class)
                .hasMessage("모집 마감된 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모임을 조기 마감한다")
    @Test
    void closeEarly() {
        Group savedGroup = saveGroup();

        groupService.closeEarly(savedMember.getId(), savedGroup.getId());

        boolean actual = groupService.findById(savedGroup.getId()).isFinished();
        assertThat(actual).isTrue();
    }

    @DisplayName("모임 목록중 두번째 페이지를 조회한다.")
    @Test
    void findAllWithPage() {
        for (int i = 0; i < PAGE_SIZE; i++) {
            saveGroup();
        }

        List<GroupSummaryResponse> summaries = IntStream.range(0, TWO_PAGE_GROUPS)
                .mapToObj(i -> saveGroup())
                .map(GroupResponseAssembler::groupSummaryResponse)
                .collect(Collectors.toList());

        GroupPageResponse actual = groupService.findAll(1);

        assertAll(
                () -> assertThat(actual.isHasNextPage()).isFalse(),
                () -> assertThat(actual.getGroups()).usingRecursiveFieldByFieldElementComparator()
                    .isEqualTo(summaries)
        );
    }


    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void delete() {
        long groupId = saveGroup().getId();
        groupService.delete(savedMember.getId(), groupId);

        assertThatThrownBy(() -> groupService.findById(groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("모집 마김된 모임을 삭제할 경우 예외가 발생한다.")
    @Test
    void deleteFinishedRecruitmentGroup() {
        Group savedGroup = saveGroup();
        Member savedMember2 = memberRepository.save(new Member("회원2", "password", "dudu"));
        savedGroup.participate(savedMember2);

        long groupId = savedGroup.getId();

        assertThatThrownBy(() -> groupService.delete(savedMember.getId(), groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("모집 마감된 모임은 수정 및 삭제할 수 없습니다.");
    }
}
