package com.woowacourse.momo.group.controller;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.controller.param.GroupParam;
import com.woowacourse.momo.group.controller.param.calendar.DurationParam;
import com.woowacourse.momo.group.controller.param.calendar.ScheduleParam;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.ParticipantService;
import com.woowacourse.momo.group.service.request.GroupRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class GroupHostControllerTest {

    private static final DurationParam DURATION_REQUEST = 이틀후_하루동안.toParam();
    private static final List<ScheduleParam> SCHEDULE_REQUESTS = ScheduleFixture.toParams(이틀후_10시부터_12시까지);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GroupService groupService;

    @Autowired
    AuthService authService;

    @Autowired
    ParticipantService participantService;

    @DisplayName("그룹이 정상적으로 생성되는 경우를 테스트한다")
    @Test
    void groupCreateTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        String accessToken = accessToken("woowa", "wooteco1!");
        GroupParam param = new GroupParam("모모의 스터디", 1L, 10,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.toDateTime(), "", "");

        mockMvc.perform(post("/api/groups/")
                        .header("Authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(param))
                )
                .andExpect(header().string("location", startsWith("/api/groups")))
                .andDo(
                        document("groupcreate",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("그룹이 정상적으로 수정되는 경우를 테스트한다")
    @Test
    void groupUpdateTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        String accessToken = accessToken("woowa", "wooteco1!");
        Long savedGroupId = saveGroup("모모의 스터디", saveMemberId, Category.STUDY);
        GroupParam param = new GroupParam("두두의 스터디", 1L, 15,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.toDateTime(), "", "");

        mockMvc.perform(put("/api/groups/" + savedGroupId)
                        .header("Authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(param))
                )
                .andExpect(status().isOk())
                .andDo(
                        document("groupupdate",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("그룹이 정상적으로 조기마감되는 경우를 테스트한다")
    @Test
    void groupCloseEarlyTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        String accessToken = accessToken("woowa", "wooteco1!");
        Long savedGroupId = saveGroup("모모의 스터디", saveMemberId, Category.STUDY);

        mockMvc.perform(post("/api/groups/" + savedGroupId + "/close")
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("groupupdate",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("그룹이 정상적으로 삭제되는 경우를 테스트한다")
    @Test
    void groupDeleteTest() throws Exception {
        Long saveHostId = saveMember("woowa", "wooteco1!", "모모");
        Long saveId = saveGroup("모모의 스터디", saveHostId, Category.STUDY);
        String accessToken = accessToken("woowa", "wooteco1!");

        mockMvc.perform(delete("/api/groups/" + saveId)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andDo(
                        document("groupdelete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    Long saveGroup(String name, Long hostId, Category category) {
        GroupRequest groupRequest = new GroupRequest(name, category.getId(), 10,
                이틀후_하루동안.toRequest(), ScheduleFixture.toRequests(이틀후_10시부터_12시까지), 내일_23시_59분까지.toRequest(),
                "", "");

        return groupService.create(hostId, groupRequest).getGroupId();
    }

    String accessToken(String id, String password) {
        LoginRequest request = new LoginRequest(id, password);

        return authService.login(request).getAccessToken();
    }

    Long saveMember(String id, String password, String name) {
        SignUpRequest request = new SignUpRequest(id, password, name);
        return authService.signUp(request);
    }
}
