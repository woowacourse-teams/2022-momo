package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateFixture._3일_후;
import static com.woowacourse.momo.fixture.DateTimeFixture._1일_후_23시_59분;
import static com.woowacourse.momo.fixture.DurationFixture._3일_후부터_7일_후까지;
import static com.woowacourse.momo.fixture.ScheduleFixture._3일_후_10시부터_12시까지;
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
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@SpringBootTest
class GroupServiceTest {

    private static final DurationRequest DURATION_REQUEST = new DurationRequest(_3일_후.getInstance(),
            _3일_후.getInstance());
    private static final List<ScheduleRequest> SCHEDULE_REQUESTS = List.of(
            new ScheduleRequest(_3일_후.getInstance(), _10시_00분.getInstance(), _12시_00분.getInstance()));

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
        return groupRepository.save(new Group("모모의 스터디", savedMember, Category.STUDY, 10,
                _3일_후부터_7일_후까지.getInstance(), _1일_후_23시_59분.getInstance(), List.of(_3일_후_10시부터_12시까지.newInstance()),
                "", ""));
    }

    @DisplayName("모임을 생성한다")
    @Test
    void create() {
        GroupRequest request = new GroupRequest("모모의 스터디", Category.STUDY.getId(), 10,
                DURATION_REQUEST, SCHEDULE_REQUESTS, _1일_후_23시_59분.getInstance(), "", "");

        groupService.create(savedMember.getId(), request);

        assertThat(groupRepository.findAll()).hasSize(1);
    }

    @DisplayName("유효하지 않은 카테고리로 모임을 생성하면 예외가 발생한다")
    @Test
    void createWithInvalidCategoryId() {
        Long categoryId = 0L;
        GroupRequest request = new GroupRequest("모모의 스터디", categoryId, 10, DURATION_REQUEST,
                SCHEDULE_REQUESTS, _1일_후_23시_59분.getInstance(), "", "");

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
                .isInstanceOf(NotFoundGroupException.class);
    }

    @DisplayName("모임 목록을 조회한다")
    @Test
    void findAll() {
        int count = 3;
        List<GroupSummaryResponse> expected = IntStream.rangeClosed(0, count)
                .mapToObj(i -> saveGroup())
                .map(GroupResponseAssembler::groupSummaryResponse)
                .collect(Collectors.toList());

        List<GroupSummaryResponse> actual = groupService.findAll();

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void delete() {
        long groupId = saveGroup().getId();
        groupService.delete(savedMember.getId(), groupId);

        assertThatThrownBy(() -> groupService.findById(groupId))
                .isInstanceOf(NotFoundGroupException.class);
    }
}
