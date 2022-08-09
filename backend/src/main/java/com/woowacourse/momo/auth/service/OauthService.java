package com.woowacourse.momo.auth.service;

import static com.woowacourse.momo.globalException.exception.ErrorCode.OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS;
import static com.woowacourse.momo.globalException.exception.ErrorCode.OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.service.dto.response.OauthLinkResponse;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.auth.support.google.GoogleConnector;
import com.woowacourse.momo.auth.support.google.GoogleProvider;
import com.woowacourse.momo.auth.support.google.dto.GoogleUserResponse;
import com.woowacourse.momo.globalException.exception.MomoException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final GoogleConnector oauthConnector;
    private final GoogleProvider oauthProvider;

    public OauthLinkResponse generateAuthUrl(String redirectUrl) {
        String oauthLink = oauthProvider.generateAuthUrl(redirectUrl);
        return new OauthLinkResponse(oauthLink);
    }

    public LoginResponse requestAccessToken(String code) {
        GoogleUserResponse response = requestUserInfo(code);

        Long memberId = findOrSaveMember(response);
        String token = jwtTokenProvider.createToken(memberId);
        return new LoginResponse(token);
    }

    private GoogleUserResponse requestUserInfo(String code) {
        ResponseEntity<GoogleUserResponse> responseEntity = oauthConnector.requestUserInfo(code);

        validateResponseStatusOk(responseEntity.getStatusCode());

        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new MomoException(OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY));
    }

    private void validateResponseStatusOk(HttpStatus status) {
        if (!status.is2xxSuccessful()) {
            throw new MomoException(OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS);
        }
    }

    private Long findOrSaveMember(GoogleUserResponse response) {
        Member member = memberRepository.findByUserId(response.getEmail())
                .orElseGet(() -> saveMember(response));

        return member.getId();
    }

    private Member saveMember(GoogleUserResponse response) {
        String userId = response.getEmail();
        String name = response.getName();
        String password = passwordEncoder.encrypt(oauthProvider.getTemporaryPassword());

        return memberRepository.save(new Member(userId, password, name));
    }
}
