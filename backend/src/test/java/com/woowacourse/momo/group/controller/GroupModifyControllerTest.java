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

import static com.woowacourse.momo.fixture.LocationFixture.잠실역_스타벅스;
import static com.woowacourse.momo.fixture.LocationFixture.잠실캠퍼스;
import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateTimeFixture.내일_23시_59분;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.controller.dto.request.GroupApiRequest;
import com.woowacourse.momo.group.controller.dto.request.calendar.DurationApiRequest;
import com.woowacourse.momo.group.controller.dto.request.calendar.ScheduleApiRequest;
import com.woowacourse.momo.group.service.GroupModifyService;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class GroupModifyControllerTest {

    private static final DurationApiRequest DURATION_REQUEST = 이틀후_하루동안.toApiRequest();
    private static final List<ScheduleApiRequest> SCHEDULE_REQUESTS = ScheduleFixture.toApiRequests(이틀후_10시부터_12시까지);

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final GroupModifyService groupModifyService;
    private final AuthService authService;
    private final MemberService memberService;

    @DisplayName("그룹이 정상적으로 생성되는 경우를 테스트한다")
    @Test
    void groupCreateTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        String accessToken = accessToken("woowa", "wooteco1!");
        GroupApiRequest request = new GroupApiRequest("모모의 스터디", 1L, 10,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.toDateTime(), 잠실캠퍼스.toApiRequest(), "");

        mockMvc.perform(post("/api/groups/")
                        .header("Authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(header().string("location", startsWith("/api/groups")))
                .andDo(
                        document("groupCreate",
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
        GroupApiRequest request = new GroupApiRequest("두두의 스터디", 1L, 15,
                DURATION_REQUEST, SCHEDULE_REQUESTS, 내일_23시_59분.toDateTime(), 잠실캠퍼스.toApiRequest(), "");

        mockMvc.perform(put("/api/groups/" + savedGroupId)
                        .header("Authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(
                        document("groupUpdate",
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
                        document("groupEarlyClose",
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
                        document("groupDelete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    Long saveGroup(String name, Long hostId, Category category) {
        GroupRequest groupRequest = new GroupRequest(name, category.getId(), 10,
                이틀후_하루동안.toRequest(), ScheduleFixture.toRequests(이틀후_10시부터_12시까지), 내일_23시_59분까지.toRequest(),
                잠실역_스타벅스.toRequest(), "");

        return groupModifyService.create(hostId, groupRequest).getGroupId();
    }

    String accessToken(String id, String password) {
        LoginRequest request = new LoginRequest(id, password);

        return authService.login(request).getAccessToken();
    }

    Long saveMember(String id, String password, String name) {
        SignUpRequest request = new SignUpRequest(id, password, name);
        return memberService.signUp(request);
    }
}
