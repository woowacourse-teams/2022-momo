package com.woowacourse.momo.auth.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthErrorCode;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.service.dto.response.OauthLinkResponse;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.auth.support.google.GoogleConnector;
import com.woowacourse.momo.auth.support.google.GoogleProvider;
import com.woowacourse.momo.auth.support.google.dto.GoogleUserResponse;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final TokenService tokenService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final GoogleConnector oauthConnector;
    private final GoogleProvider oauthProvider;

    public OauthLinkResponse generateAuthUrl(String redirectUrl) {
        String oauthLink = oauthProvider.generateAuthUrl(redirectUrl);
        return new OauthLinkResponse(oauthLink);
    }

    @Transactional
    public LoginResponse requestAccessToken(String code, String redirectUrl) {
        GoogleUserResponse response = requestUserInfo(code, redirectUrl);

        Member member = findOrSaveMember(response);
        String token = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        tokenService.synchronizeRefreshToken(member, refreshToken);

        return new LoginResponse(token, refreshToken);
    }

    private GoogleUserResponse requestUserInfo(String code, String redirectUrl) {
        ResponseEntity<GoogleUserResponse> responseEntity = oauthConnector.requestUserInfo(code, redirectUrl);

        validateResponseStatusIsOk(responseEntity.getStatusCode());

        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new MomoException(AuthErrorCode.OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY));
    }

    private void validateResponseStatusIsOk(HttpStatus status) {
        if (!status.is2xxSuccessful()) {
            throw new MomoException(AuthErrorCode.OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS);
        }
    }

    private Member findOrSaveMember(GoogleUserResponse response) {
        return memberRepository.findByUserId(UserId.oauth(response.getEmail()))
                .orElseGet(() -> saveMember(response));
    }

    private Member saveMember(GoogleUserResponse response) {
        UserId userId = UserId.oauth(response.getEmail());
        UserName userName = UserName.from(response.getName());
        Password password = Password.encrypt(oauthProvider.getTemporaryPassword(), passwordEncoder);

        return memberRepository.save(new Member(userId, password, userName));
    }
}
