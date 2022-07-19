package com.woowacourse.momo.group.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.woowacourse.momo.group.fixture.GroupFixture._10시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._12시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.dto.request.LoginRequest;
import com.woowacourse.momo.auth.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.dto.request.DurationRequest;
import com.woowacourse.momo.group.service.dto.request.GroupRequest;
import com.woowacourse.momo.group.service.dto.request.ScheduleRequest;


@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class GroupControllerTest {
    private static final DurationRequest DURATION_REQUEST = new DurationRequest(_7월_1일, _7월_1일);
    private static final List<ScheduleRequest> SCHEDULE_REQUESTS = List.of(
            new ScheduleRequest(_7월_1일, _10시_00분, _12시_00분));

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GroupService groupService;

    @Autowired
    AuthService authService;

    @DisplayName("그룹이 정상적으로 생성되는 경우를 테스트한다")
    @Test
    void groupCreateTest() throws Exception {
        Long saveMemberId = saveMember();
        String accessToken = accessToken();
        GroupRequest groupRequest = new GroupRequest("모모의 스터디", saveMemberId, 1L, DURATION_REQUEST,
                SCHEDULE_REQUESTS, LocalDateTime.now(), "", "");

        mockMvc.perform(post("/api/groups/")
                .header("Authorization", "bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(groupRequest))
        ).andExpect(header().string("location", startsWith("/api/groups")));
    }

    @DisplayName("그룹이 정상적으로 수정되는 경우를 테스트한다")
    @Test
    void groupUpdateTest() {

    }

    @DisplayName("그룹이 정상적으로 삭제되는 경우를 테스트한다")
    @Test
    void groupDeleteTest() throws Exception {
        Long saveMemberId = saveMember();
        Long saveId = saveGroup(saveMemberId);
        String accessToken = accessToken();

        mockMvc.perform(delete("/api/groups/" + saveId)
                .header("Authorization", "bearer " + accessToken)
        ).andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @DisplayName("하나의 그룹을 가져오는 경우를 테스트한다")
    @Test
    void groupGetTest() throws Exception {
        Long saveMemberId = saveMember();
        Long saveId = saveGroup(saveMemberId);
        String accessToken = accessToken();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups/" + saveId)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("name", is("모모의 스터디")));
    }

    @DisplayName("그룹 목록을 가져오는 경우를 테스트한다")
    @Test
    void groupGetListTest() throws Exception {
        Long saveMemberId = saveMember();
        saveGroup(saveMemberId);
        saveGroup(saveMemberId);
        String accessToken = accessToken();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$[0].name", is("모모의 스터디")))
                .andExpect(jsonPath("$[1].name", is("모모의 스터디")));
    }

    Long saveGroup(Long hostId) {
        GroupRequest groupRequest = new GroupRequest("모모의 스터디", hostId, 1L, DURATION_REQUEST,
                SCHEDULE_REQUESTS, LocalDateTime.now(), "", "");

        return groupService.create(groupRequest).getGroupId();
    }

    String accessToken() {
        LoginRequest request = new LoginRequest("woowa@woowa.com", "wooteco1!");

        return authService.login(request).getAccessToken();
    }

    Long saveMember() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");
        return authService.signUp(request);
    }
}
