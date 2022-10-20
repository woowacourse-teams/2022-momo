package com.woowacourse.momo.group.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.woowacourse.momo.fixture.LocationFixture.잠실캠퍼스;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.group.service.GroupModifyService;
import com.woowacourse.momo.group.service.ParticipateService;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class ParticipateControllerTest {

    private static final String BASE_URL = "/api/groups/";
    private static final String RESOURCE = "/participants";

    private final MockMvc mockMvc;
    private final ParticipateService participateService;
    private final AuthService authService;
    private final MemberService memberService;
    private final GroupModifyService groupModifyService;
    private final GroupFindService groupFindService;

    @DisplayName("모임에 참여한다")
    @Test
    void participate() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant", "participant");
        String accessToken = accessToken("participant");

        mockMvc.perform(post(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("participate",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("존재하지 않는 모임에 참여할 수 없다")
    @Test
    void participateNotExistGroup() throws Exception {
        Long participantId = saveMember("participant", "participant");
        String accessToken = accessToken("participant");

        mockMvc.perform(post(BASE_URL + 0 + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("GROUP_001")));
    }

    @DisplayName("탈퇴한 사용자는 모임에 참여할 수 없다")
    @Test
    void participateDeletedMember() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant", "participant");
        String accessToken = accessToken("participant");
        deleteMember(participantId);

        mockMvc.perform(post(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("MEMBER_002")));
    }

    @DisplayName("모임에 이미 속해있을 경우 모임에 참여할 수 없다")
    @Test
    void reParticipate() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant", "participant");
        String accessToken = accessToken("participant");
        participateMember(groupId, participantId);

        mockMvc.perform(post(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("GROUP_016")));
    }

    @DisplayName("모임 정원이 가득 찬 경우 참여를 할 수 없다")
    @Test
    void participateFullGroup() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroupWithSetCapacity(hostId, 1);
        Long participantId = saveMember("participant", "participant");
        String accessToken = accessToken("participant");

        mockMvc.perform(post(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("GROUP_013")));
    }

    @DisplayName("모임의 참여자 목록을 조회한다")
    @Test
    void findParticipants() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);

        mockMvc.perform(get(BASE_URL + groupId + RESOURCE)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("findParticipants",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("존재하지 않는 모임의 참여자 목록을 조회할 수 없다")
    @Test
    void findParticipantsNotExistGroup() throws Exception {
        mockMvc.perform(get(BASE_URL + 0 + RESOURCE)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("GROUP_001")));
    }

    @DisplayName("모임에 탈퇴한다")
    @Test
    void deleteParticipant() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant", "participant");
        participateMember(groupId, participantId);
        String accessToken = accessToken("participant");

        mockMvc.perform(delete(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().isNoContent())
                .andDo(
                        document("deleteParticipant",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()))
                );
    }

    @DisplayName("주최자일 경우 모임에 탈퇴할 수 없다")
    @Test
    void deleteHost() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        String accessToken = accessToken("host");

        mockMvc.perform(delete(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("모임에 참여하지 않았으면 탈퇴할 수 없다")
    @Test
    void deleteNotParticipant() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long memberId = saveMember("member", "member");
        String accessToken = accessToken("member");

        mockMvc.perform(delete(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().isForbidden())
                .andDo(
                        document("deletenotparticipant",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()))
                );
    }

    @DisplayName("모집 마감이 끝난 모임에는 탈퇴할 수 없다")
    @Test
    void deleteDeadline() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant", "participant");
        participateMember(groupId, participantId);
        String accessToken = accessToken("participant");

        GroupFixture.setDeadlinePast(groupFindService.findGroup(groupId), 1);

        mockMvc.perform(delete(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("조기 종료된 모임에는 탈퇴할 수 없다")
    @Test
    void deleteEarlyClosed() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant", "participant");
        participateMember(groupId, participantId);
        String accessToken = accessToken("participant");

        groupModifyService.closeEarly(hostId, groupId);

        mockMvc.perform(delete(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().isBadRequest());
    }

    Long saveMember(String userId, String userName) {
        SignUpRequest request = new SignUpRequest(userId, "wooteco1!", userName);
        return memberService.signUp(request);
    }

    String accessToken(String userId) {
        LoginRequest request = new LoginRequest(userId, "wooteco1!");

        return authService.login(request).getAccessToken();
    }

    void deleteMember(Long memberId) {
        memberService.deleteById(memberId);
    }

    Long saveGroup(Long hostId) {
        return saveGroupWithSetCapacity(hostId, 10);
    }

    Long saveGroupWithSetCapacity(Long hostId, int capacity) {
        GroupRequest request = new GroupRequest("모모의 스터디", 1L, capacity,
                이틀후_하루동안.toRequest(), ScheduleFixture.toRequests(이틀후_10시부터_12시까지),
                내일_23시_59분까지.toRequest(), 잠실캠퍼스.toRequest(), "");

        return groupModifyService.create(hostId, request).getGroupId();
    }

    void participateMember(Long groupId, Long memberId) {
        participateService.participate(groupId, memberId);
    }
}
