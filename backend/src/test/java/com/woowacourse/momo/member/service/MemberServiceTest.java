package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.GroupFixture.MOMO_STUDY;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.domain.TokenRepository;
import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.GroupRepository;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.service.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.service.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.service.dto.response.MyInfoResponse;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberFindService memberFindService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupFindService groupFindService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    private Password password;
    private Member savedHost;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("momo123!", new SHA256Encoder());
        savedHost = memberRepository.save(new Member(UserId.momo("주최자"), password, "momo"));
    }

    @DisplayName("회원 정보를 조회한다")
    @Test
    void findById() {
        SignUpRequest request = new SignUpRequest("woowa", "wooteco1!", "모모");
        Long memberId = authService.signUp(request);

        MyInfoResponse response = memberService.findById(memberId);

        assertAll(
                () -> assertThat(response.getId()).isEqualTo(memberId),
                () -> assertThat(response.getUserId()).isEqualTo(request.getUserId()),
                () -> assertThat(response.getName()).isEqualTo(request.getName())
        );
    }

    @DisplayName("존재하지 않는 회원 정보를 조회하는 경우 예외가 발생한다")
    @Test
    void findByIdNotExist() {
        assertThatThrownBy(() -> memberService.findById(1000L))
                .isInstanceOf(MomoException.class)
                .hasMessageContaining("멤버가 존재하지 않습니다.");
    }

    @DisplayName("회원 이름을 수정한다")
    @Test
    void updateName() {
        Long memberId = createMember();
        Member beforeMember = memberFindService.findMember(memberId);
        String beforeName = beforeMember.getUserName();

        ChangeNameRequest request = new ChangeNameRequest("무무");
        memberService.updateName(memberId, request);

        Member member = memberFindService.findMember(memberId);
        assertThat(member.getUserName()).isNotEqualTo(beforeName);
    }

    @DisplayName("존재하지 않는 회원의 이름을 수정하는 경우 예외가 발생한다")
    @Test
    void updateNameNotExist() {
        ChangeNameRequest request = new ChangeNameRequest("무무");

        assertThatThrownBy(() -> memberService.updateName(1000L, request)).isInstanceOf(MomoException.class)
                .hasMessageContaining("멤버가 존재하지 않습니다.");
    }

    @DisplayName("비밀번호를 업데이트한다")
    @Test
    void updatePassword() {
        String beforePassword = "wooteco1!";
        SignUpRequest signUpRequest = new SignUpRequest("woowa", beforePassword, "모모");
        Long memberId = authService.signUp(signUpRequest);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("newPassword123!", beforePassword);
        memberService.updatePassword(memberId, changePasswordRequest);

        Member member = memberFindService.findMember(memberId);

        String encryptedPassword = passwordEncoder.encrypt(beforePassword);
        assertThat(member.getPassword()).isNotEqualTo(encryptedPassword);
    }

    @DisplayName("잘못된 현재 비밀번호로 비밀번호를 업데이트시 예외가 발생한다")
    @Test
    void updatePasswordWithWrongPassword() {
        String password = "wooteco1!";
        SignUpRequest signUpRequest = new SignUpRequest("woowa", password, "모모");
        Long memberId = authService.signUp(signUpRequest);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("newPassword123!", "wrongPassword");
        assertThatThrownBy(() -> memberService.updatePassword(memberId, changePasswordRequest))
                .isInstanceOf(MomoException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        Long memberId = createMember();

        memberService.deleteById(memberId);

        assertAll(
                () -> assertThat(tokenRepository.findByMemberId(memberId)).isEmpty(),
                () -> assertThatThrownBy(() -> memberService.findById(memberId))
                        .isInstanceOf(MomoException.class)
                        .hasMessage("탈퇴한 멤버입니다.")
        );
    }

    @DisplayName("존재하지 않는 회원 정보를 삭제한다")
    @Test
    void deleteNotExistMember() {
        assertThatThrownBy(() -> memberService.findById(1000L))
                .isInstanceOf(MomoException.class)
                .hasMessage("멤버가 존재하지 않습니다.");
    }

    @DisplayName("회원 정보 삭제 시 참여한 모임 중 진행중인 모임이 있을 경우 모임에 탈퇴시킨다")
    @Test
    void deleteAndLeave() {
        Group group = saveGroup();
        Long memberId = createMember();
        Member member = memberFindService.findMember(memberId);
        group.participate(member);

        memberService.deleteById(memberId);

        List<Group> groups = groupFindService.findParticipatedGroups(member);
        assertThat(groups).isEmpty();
    }

    @DisplayName("회원 정보 삭제 시 주최한 모임 중 진행중인 모임이 있을 경우 탈퇴할 수 없다")
    @Test
    void deleteExistInProgressGroup() {
        Group group = saveGroup();

        assertThatThrownBy(() -> memberService.deleteById(savedHost.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("진행중인 모임이 있어 탈퇴할 수 없습니다.");
    }

    private Long createMember() {
        SignUpRequest request = new SignUpRequest("woowa", "wooteco1!", "모모");
        return authService.signUp(request);
    }

    private Group saveGroup() {
        Group group = MOMO_STUDY.builder()
                .capacity(3)
                .toGroup(savedHost);
        return groupRepository.save(group);
    }
}
