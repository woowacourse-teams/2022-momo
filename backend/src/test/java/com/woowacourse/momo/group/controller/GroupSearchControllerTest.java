package com.woowacourse.momo.group.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.woowacourse.momo.fixture.calendar.DeadlineFixture.내일_23시_59분까지;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
import com.woowacourse.momo.group.service.GroupService;
import com.woowacourse.momo.group.service.ParticipantService;
import com.woowacourse.momo.group.service.request.GroupRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class GroupSearchControllerTest {

    private static final int TWO_PAGE_WITH_EIGHT_GROUP_AT_TWO_PAGE = 20;

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

    @DisplayName("하나의 그룹을 가져오는 경우를 테스트한다")
    @Test
    void groupGetTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        Long saveId = saveGroup("모모의 스터디", saveMemberId, Category.STUDY);
        String accessToken = accessToken("woowa", "wooteco1!");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups/" + saveId)
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("name", is("모모의 스터디")))
                .andDo(
                        document("groupget",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("그룹 목록을 가져오는 경우를 테스트한다")
    @Test
    void groupGetListTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        saveGroup("모모의 JPA 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 술파티", saveMemberId, Category.DRINK);
        saveGroup("모모의 리엑트 스터디", saveMemberId, Category.STUDY);

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "/api/groups?category=1&keyword=모모&excludeFinished=true&orderByDeadline=true&page=0"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("groups[0].name", is("모모의 리엑트 스터디")))
                .andExpect(jsonPath("groups[1].name", is("모모의 JPA 스터디")))
                .andDo(
                        document("grouplist",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("내가 참여한 그룹 목록을 가져오는 경우를 테스트한다")
    @Test
    void groupGetParticipatedListTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        String token = accessToken("woowa", "wooteco1!");
        saveGroup("모모의 JPA 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 술파티", saveMemberId, Category.DRINK);
        saveGroup("모모의 리액트 스터디", saveMemberId, Category.STUDY);

        mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/groups/me/participated?category=1&keyword=모모&excludeFinished=true&orderByDeadline=true&page=0")
                        .header("Authorization", "bearer " + token))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("groups[0].name", is("모모의 리액트 스터디")))
                .andExpect(jsonPath("groups[1].name", is("모모의 JPA 스터디")))
                .andDo(
                        document("participatedgrouplist",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("로그인하지 않은 경우 내가 참여한 그룹 목록을 가져오는 경우 401 응답 코드를 반환한다")
    @Test
    void groupGetParticipatedListWithoutLoginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "/api/groups/me/participated?category=1&keyword=모모&excludeFinished=true&orderByDeadline=true&page=0"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @DisplayName("내가 주최한 그룹 목록을 가져오는 경우를 테스트한다")
    @Test
    void groupGetHostedListTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        String token = accessToken("woowa", "wooteco1!");
        saveGroup("모모의 JPA 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 술파티", saveMemberId, Category.DRINK);
        saveGroup("모모의 리엑트 스터디", saveMemberId, Category.STUDY);

        mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/groups/me/hosted?category=1&keyword=모모&excludeFinished=true&orderByDeadline=true&page=0")
                        .header("Authorization", "bearer " + token))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("groups[0].name", is("모모의 리엑트 스터디")))
                .andExpect(jsonPath("groups[1].name", is("모모의 JPA 스터디")))
                .andDo(
                        document("hostedgrouplist",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("로그인하지 않은 경우 내가 주최한 그룹 목록을 가져오는 경우 401 응답 코드를 반환한다")
    @Test
    void groupGetHostedListWithoutLoginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups/me/hosted"))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @DisplayName("카테고리별 그룹 목록을 가져오는 경우를 테스트한다")
    @Test
    void groupGetListByCategoryTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        saveGroup("모모의 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 술파티", saveMemberId, Category.DRINK);
        saveGroup("구구의 스터디", saveMemberId, Category.STUDY);
        saveGroup("브리의 스터디", saveMemberId, Category.STUDY);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups?category=1&page=0"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("groups[0].name", is("브리의 스터디")))
                .andExpect(jsonPath("groups[1].name", is("구구의 스터디")))
                .andExpect(jsonPath("groups[2].name", is("무무의 스터디")))
                .andExpect(jsonPath("groups[3].name", is("모모의 스터디")))
                .andDo(
                        document("grouplistbycategory",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("키워드로 그룹 목록을 가져오는 경우를 테스트한다")
    @Test
    void groupGetListByKeywordTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        saveGroup("모모의 스터디", saveMemberId, Category.STUDY);
        saveGroup("무무의 스터디", saveMemberId, Category.STUDY);
        saveGroup("모모의 술파티", saveMemberId, Category.DRINK);
        saveGroup("구구의 스터디", saveMemberId, Category.STUDY);
        saveGroup("브리의 스터디", saveMemberId, Category.STUDY);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups?keyword=모모&page=0"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("groups[0].name", is("모모의 술파티")))
                .andExpect(jsonPath("groups[1].name", is("모모의 스터디")))
                .andDo(
                        document("grouplistbykeyword",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("그룹 목록을 페이지네이션 하여 가져온 결과를 출력한다")
    @Test
    void groupGetListWithPaginationTest() throws Exception {
        Long saveMemberId = saveMember("woowa", "wooteco1!", "모모");
        for (int i = 0; i < TWO_PAGE_WITH_EIGHT_GROUP_AT_TWO_PAGE; i++) {
            saveGroup("모모의 스터디" + i, saveMemberId, Category.STUDY);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups?page=1"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("groups", hasSize(8)));
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
