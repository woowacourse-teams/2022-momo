package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.group.fixture.GroupFixture._10시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._12시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._6월_30일_23시_59분;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일부터_2일까지;
import static com.woowacourse.momo.group.fixture.ScheduleFixture._7월_1일_10시부터_12시까지;

import java.time.LocalDateTime;
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
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@SpringBootTest
class GroupServiceTest {

    private static final DurationRequest DURATION_REQUEST = new DurationRequest(_7월_1일, _7월_1일);
    private static final List<ScheduleRequest> SCHEDULE_REQUESTS = List.of(
            new ScheduleRequest(_7월_1일, _10시_00분, _12시_00분));

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(new Member("회원"));
    }

    private Group saveGroup() {
        return groupRepository.save(new Group("모모의 스터디", savedMember.getId(), Category.STUDY,
                _7월_1일부터_2일까지, _6월_30일_23시_59분, List.of(_7월_1일_10시부터_12시까지.newInstance()), "", ""));
    }

    @DisplayName("모임을 생성한다")
    @Test
    void create() {
        GroupRequest request = new GroupRequest("모모의 스터디", savedMember.getId(), Category.STUDY.getId(),
                DURATION_REQUEST, SCHEDULE_REQUESTS, LocalDateTime.now(), "", "");

        groupService.create(request);

        assertThat(groupRepository.findAll()).hasSize(1);
    }

    @DisplayName("유효하지 않은 카테고리로 모임을 생성하면 예외가 발생한다")
    @Test
    void createWithInvalidCategoryId() {
        Long categoryId = 0L;
        GroupRequest request = new GroupRequest("모모의 스터디", 1L, categoryId, DURATION_REQUEST,
                SCHEDULE_REQUESTS, LocalDateTime.now(), "", "");

        assertThatThrownBy(() -> groupService.create(request))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("카테고리를 찾을 수 없습니다.");
    }

    @DisplayName("모임을 조회한다")
    @Test
    void findById() {
        Group savedGroup = saveGroup();
        GroupResponse expected = GroupResponseAssembler.groupResponse(savedGroup, savedMember);

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
        List<GroupResponse> expected = IntStream.rangeClosed(0, count)
                .mapToObj(i -> saveGroup())
                .map(group -> GroupResponseAssembler.groupResponse(group, savedMember))
                .collect(Collectors.toList());

        List<GroupResponse> actual = groupService.findAll();

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void delete() {
        long groupId = saveGroup().getId();
        groupService.delete(groupId);

        assertThatThrownBy(() -> groupService.findById(groupId))
                .isInstanceOf(NotFoundGroupException.class);
    }
}
