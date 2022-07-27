package com.woowacourse.momo.participant.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.woowacourse.momo.fixture.DateFixture._7월_1일;
import static com.woowacourse.momo.fixture.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.TimeFixture._12시_00분;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.dto.request.LoginRequest;
import com.woowacourse.momo.auth.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.participant.service.ParticipantService;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
public class ParticipantControllerTest {

    private static final DurationRequest DURATION_REQUEST = new DurationRequest(_7월_1일.getInstance(), _7월_1일.getInstance());
    private static final List<ScheduleRequest> SCHEDULE_REQUESTS = List.of(
            new ScheduleRequest(_7월_1일.getInstance(), _10시_00분.getInstance(), _12시_00분.getInstance()));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GroupService groupService;

    @DisplayName("모임에 참여한다")
    @Test
    void participate() throws Exception {
        Long hostId = saveMember("host@woowacourse.com");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant@woowacourse.com");
        String accessToken = accessToken("participant@woowacourse.com");

        mockMvc.perform(post("/api/groups/" + groupId + "/participants")
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
        Long participantId = saveMember("participant@woowacourse.com");
        String accessToken = accessToken("participant@woowacourse.com");

        mockMvc.perform(post("/api/groups/" + 0 + "/participants")
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("존재하지 않는 모임입니다.")))
                .andDo(
                        document("participatenotexistgroup",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("존재하지 않는 사용자는 모임에 참여할 수 없다")
    @Test
    void participateNotExistMember() throws Exception {
        Long hostId = saveMember("host@woowacourse.com");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant@woowacourse.com");
        String accessToken = accessToken("participant@woowacourse.com");
        deleteMember(participantId);

        mockMvc.perform(post("/api/groups/" + groupId + "/participants")
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("존재하지 않는 회원입니다.")))
                .andDo(
                        document("participatenotexistmember",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("모임의 주최자일 경우 모임에 참여할 수 없다")
    @Test
    void participateHost() throws Exception {
        Long hostId = saveMember("host@woowacourse.com");
        Long groupId = saveGroup(hostId);
        String accessToken = accessToken("host@woowacourse.com");

        mockMvc.perform(post("/api/groups/" + groupId + "/participants")
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("주최자는 모임에 참여할 수 없습니다.")))
                .andDo(
                        document("participatehost",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("모임에 이미 속해있을 경우 모임에 참여할 수 없다")
    @Test
    void participateParticipant() throws Exception {
        Long hostId = saveMember("host@woowacourse.com");
        Long groupId = saveGroup(hostId);
        Long participantId = saveMember("participant@woowacourse.com");
        String accessToken = accessToken("participant@woowacourse.com");
        participateMember(groupId, participantId);

        mockMvc.perform(post("/api/groups/" + groupId + "/participants")
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("이미 참여한 모임입니다.")))
                .andDo(
                        document("participateparticipant",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("모임의 참여자 목록을 조회한다")
    @Test
    void findParticipants() throws Exception {
        Long hostId = saveMember("host@woowacourse.com");
        Long groupId = saveGroup(hostId);

        mockMvc.perform(get("/api/groups/" + groupId + "/participants")
                )
                .andExpect(status().isOk())
                .andDo(
                        document("findparticipants",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("존재하지 않는 모임의 참여자 목록을 조회할 수 없다")
    @Test
    void findParticipantsNotExistGroup() throws Exception {
        mockMvc.perform(get("/api/groups/" + 0 + "/participants")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("존재하지 않는 모임입니다.")))
                .andDo(
                        document("findparticipantsnotexistgroup",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    Long saveMember(String email) {
        SignUpRequest request = new SignUpRequest(email, "wooteco1!", "momo");
        return authService.signUp(request);
    }

    String accessToken(String email) {
        LoginRequest request = new LoginRequest(email, "wooteco1!");

        return authService.login(request).getAccessToken();
    }

    void deleteMember(Long memberId) {
        memberService.deleteById(memberId);
    }

    Long saveGroup(Long hostId) {
        GroupRequest groupRequest = new GroupRequest("모모의 스터디", 1L, 10, DURATION_REQUEST,
                SCHEDULE_REQUESTS, LocalDateTime.now(), "", "");

        return groupService.create(hostId, groupRequest).getGroupId();
    }

    void participateMember(Long groupId, Long memberId) {
        participantService.participate(groupId, memberId);
    }
}
