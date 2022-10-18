package com.woowacourse.momo.favorite.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.woowacourse.momo.favorite.service.FavoriteService;
import com.woowacourse.momo.fixture.calendar.ScheduleFixture;
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
class FavoriteControllerTest {

    private static final String BASE_URL = "/api/groups/";
    private static final String RESOURCE = "/like";

    private final MockMvc mockMvc;
    private final AuthService authService;
    private final GroupModifyService groupModifyService;
    private final FavoriteService favoriteService;
    private final MemberService memberService;

    @DisplayName("모임을 찜한다")
    @Test
    void like() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long memberId = saveMember("member", "member");
        String accessToken = accessToken("member");

        mockMvc.perform(post(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("like",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    @DisplayName("모임을 찜하기를 취소한다")
    @Test
    void cancelLike() throws Exception {
        Long hostId = saveMember("host", "host");
        Long groupId = saveGroup(hostId);
        Long memberId = saveMember("member", "member");
        likeMember(groupId, memberId);
        String accessToken = accessToken("member");

        mockMvc.perform(delete(BASE_URL + groupId + RESOURCE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isNoContent())
                .andDo(
                        document("cancelLike",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()
                                )
                        )
                );
    }

    Long saveMember(String userId, String userName) {
        SignUpRequest request = new SignUpRequest(userId, "wooteco1!", userName);
        return memberService.signUp(request);
    }

    String accessToken(String userId) {
        LoginRequest request = new LoginRequest(userId, "wooteco1!");

        return authService.login(request).getAccessToken();
    }

    Long saveGroup(Long hostId) {
        GroupRequest request = new GroupRequest("모모의 스터디", 1L, 10,
                이틀후_하루동안.toRequest(), ScheduleFixture.toRequests(이틀후_10시부터_12시까지),
                내일_23시_59분까지.toRequest(), 잠실캠퍼스.toRequest(), "");

        return groupModifyService.create(hostId, request).getGroupId();
    }

    void likeMember(Long groupId, Long memberId) {
        favoriteService.like(groupId, memberId);
    }
}
