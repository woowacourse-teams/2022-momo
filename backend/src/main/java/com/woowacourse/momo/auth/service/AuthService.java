package com.woowacourse.momo.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthErrorCode;
import com.woowacourse.momo.auth.exception.AuthException;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.response.AccessTokenResponse;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
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

    public AccessTokenResponse reissueAccessToken(Long memberId) {
        boolean isExistMember = memberRepository.existsById(memberId);
        if (!isExistMember) {
            throw new AuthException(AuthErrorCode.AUTH_INVALID_TOKEN);
        }
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        return new AccessTokenResponse(accessToken);
    }

    @Transactional
    public void logout(Long memberId) {
        tokenService.deleteByMemberId(memberId);
    }
}
