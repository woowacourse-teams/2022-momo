package com.woowacourse.momo.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.service.dto.response.AccessTokenResponse;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;
import com.woowacourse.momo.member.service.MemberFindService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final TokenService tokenService;
    private final MemberFindService memberFindService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        UserId userId = UserId.momo(request.getUserId());
        Password password = Password.encrypt(request.getPassword(), passwordEncoder);
        Member member = memberFindService.findByUserIdAndPassword(userId, password);
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        tokenService.synchronizeRefreshToken(member, refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public Long signUp(SignUpRequest request) {
        UserId userId = UserId.momo(request.getUserId());
        UserName userName = UserName.from(request.getName());
        Password password = Password.encrypt(request.getPassword(), passwordEncoder);
        Member member = new Member(userId, password, userName);

        validateUserIsNotExist(userId);
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void validateUserIsNotExist(UserId userId) {
        Optional<Member> member = memberRepository.findByUserId(userId);
        if (member.isPresent()) {
            throw new MomoException(GlobalErrorCode.SIGNUP_ALREADY_REGISTER);
        }
    }

    public AccessTokenResponse reissueAccessToken(Long memberId) {
        boolean isExistMember = memberRepository.existsById(memberId);
        if (!isExistMember) {
            throw new MomoException(GlobalErrorCode.AUTH_INVALID_TOKEN);
        }
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        return new AccessTokenResponse(accessToken);
    }

    @Transactional
    public void logout(Long memberId) {
        tokenService.deleteByMemberId(memberId);
    }
}
