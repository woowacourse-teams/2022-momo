package com.woowacourse.momo.group.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._12시_00분;

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
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.domain.group.Capacity;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupName;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;
import com.woowacourse.momo.group.service.dto.response.GroupPageResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponse;
import com.woowacourse.momo.group.service.dto.response.GroupResponseAssembler;
import com.woowacourse.momo.group.service.dto.response.GroupSummaryResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.participant.service.ParticipantService;

@Transactional
@SpringBootTest
class GroupServiceTest {

    private static final DurationRequest DURATION_REQUEST = new DurationRequest(이틀후.getDate(),
            이틀후.getDate());
    private static final List<ScheduleRequest> SCHEDULE_REQUESTS = List.of(
            new ScheduleRequest(이틀후.getDate(), _10시_00분.getTime(), _12시_00분.getTime()));
    private static final int PAGE_SIZE = 12;
    private static final int TWO_PAGE_GROUPS = 8;

    @Autowired
    private GroupService groupService;

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
        return groupRepository.save(new Group(new GroupName(name), savedHost, category, new Capacity(3),
                이틀후부터_5일동안.getDuration(), new Deadline(내일_23시_59분.getDateTime()),
                new Schedules(List.of(이틀후_10시부터_12시까지.getSchedule())), "", ""));
    }

    @DisplayName("모임을 생성한다")
    @Test
    void create() {
        GroupRequest request = new GroupRequest("모모의 스터디", Category.STUDY.getId(), 10,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        groupService.create(savedHost.getId(), request);

        assertThat(groupRepository.findAll()).hasSize(1);
    }

    @DisplayName("유효하지 않은 카테고리로 모임을 생성하면 예외가 발생한다")
    @Test
    void createWithInvalidCategoryId() {
        Long categoryId = 0L;
        GroupRequest request = new GroupRequest("모모의 스터디", categoryId, 10, DURATION_REQUEST,
                SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        assertThatThrownBy(() -> groupService.create(savedHost.getId(), request))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
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

    @DisplayName("모임을 수정한다")
    @Test
    void update() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        GroupUpdateRequest groupRequest = new GroupUpdateRequest("두두의 스터디", 1L, 2,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        groupService.update(savedHost.getId(), savedGroup.getId(), groupRequest);

        assertThat(groupService.findGroup(savedGroup.getId()))
                .usingRecursiveComparison()
                .ignoringFields("host", "finished")
                .isEqualTo(groupRequest);
    }

    @DisplayName("존재하지 않는 모임을 수정하는 경우 예외가 발생한다")
    @Test
    void updateNotExistGroup() {
        GroupUpdateRequest groupRequest = new GroupUpdateRequest("두두의 스터디", 1L, 2,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        assertThatThrownBy(() -> groupService.update(savedHost.getId(), 1000L, groupRequest))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임ㅇ르 수정할 경우 예외가 발생한다")
    @Test
    void updateMemberIsNotHost() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        GroupUpdateRequest groupRequest = new GroupUpdateRequest("두두의 스터디", 1L, 2,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        assertThatThrownBy(() -> groupService.update(savedMember1.getId(), savedGroup.getId(), groupRequest))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다.");
    }

    @DisplayName("주최자 외 참여자가 있을 때 모임을 수정하면 예외가 발생한다")
    @Test
    void updateExistParticipants() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        savedGroup.participate(savedMember1);
        GroupUpdateRequest groupRequest = new GroupUpdateRequest("두두의 스터디", 1L, 3,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        assertThatThrownBy(() -> groupService.update(savedHost.getId(), savedGroup.getId(), groupRequest))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모집 마김된 모임을 수정할 경우 예외가 발생한다")
    @Test
    void updateFinishedRecruitmentGroup() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        savedGroup.participate(savedMember1);
        savedGroup.participate(savedMember2);
        long groupId = savedGroup.getId();

        GroupUpdateRequest groupRequest = new GroupUpdateRequest("두두의 스터디", 1L, 3,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.getDateTime(), "", "");

        assertThatThrownBy(() -> groupService.update(savedHost.getId(), groupId, groupRequest))
                .isInstanceOf(MomoException.class)
                .hasMessage("모집 마감된 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모임을 조기 마감한다")
    @Test
    void closeEarly() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);

        groupService.closeEarly(savedHost.getId(), savedGroup.getId());

        boolean actual = groupService.findGroup(savedGroup.getId()).isFinished();
        assertThat(actual).isTrue();
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 조기마감 할 경우 예외가 발생한다")
    @Test
    void closeEarlyMemberIsNotHost() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);

        assertThatThrownBy(() -> groupService.closeEarly(savedMember1.getId(), savedGroup.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다.");
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

    @DisplayName("식별자를 통해 모임을 삭제한다")
    @Test
    void delete() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        groupService.delete(savedHost.getId(), savedGroup.getId());

        assertThatThrownBy(() -> groupService.findGroup(savedGroup.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("존재하지 않는 모임입니다.");
    }

    @DisplayName("주최자가 아닌 사용자가 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteNotHost() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);

        assertThatThrownBy(() -> groupService.delete(savedMember1.getId(), savedGroup.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다.");
    }

    @DisplayName("주최자를 제외하고 참여자가 있을 경우 모임을 삭제하면 예외가 발생한다")
    @Test
    void deleteExistParticipants() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        participantService.participate(savedGroup.getId(), savedMember1.getId());

        assertThatThrownBy(() -> groupService.delete(savedHost.getId(), savedGroup.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다.");
    }

    @DisplayName("모집 마김된 모임을 삭제할 경우 예외가 발생한다")
    @Test
    void deleteFinishedRecruitmentGroup() {
        Group savedGroup = saveGroup("모모의 스터디", Category.STUDY);
        savedGroup.participate(savedMember1);
        savedGroup.participate(savedMember2);

        long groupId = savedGroup.getId();

        assertThatThrownBy(() -> groupService.delete(savedHost.getId(), groupId))
                .isInstanceOf(MomoException.class)
                .hasMessage("모집 마감된 모임은 수정 및 삭제할 수 없습니다.");
    }
}
