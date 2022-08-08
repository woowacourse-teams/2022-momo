package com.woowacourse.momo.auth.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.auth.support.google.GoogleConnector;
import com.woowacourse.momo.auth.support.google.GoogleProvider;
import com.woowacourse.momo.auth.support.google.dto.GoogleUserResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider JwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final GoogleConnector oauthConnector;
    private final GoogleProvider oauthProvider;

    public String generateAuthUrl() {
        return oauthProvider.generateAuthUrl();
    }

    public LoginResponse requestAccessToken(String code) {
        GoogleUserResponse response = requestUserInfo(code);

        Long memberId = findOrSaveMember(response);
        String token = JwtTokenProvider.createToken(memberId);
        return new LoginResponse(token);
    }

    private GoogleUserResponse requestUserInfo(String code) {
        ResponseEntity<GoogleUserResponse> responseEntity = oauthConnector.requestUserInfo(code);

        validateResponseStatusOk(responseEntity.getStatusCode());

        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new IllegalArgumentException("Google Oauth2 Request UserInfo - Non Exist Body"));
    }

    private void validateResponseStatusOk(HttpStatus status) {
        if (!status.is2xxSuccessful()) {
            throw new IllegalArgumentException("Google Oauth2 Request UserInfo - Http Status 2xx Failed");
        }
    }

    private Long findOrSaveMember(GoogleUserResponse response) {
        Member member = memberRepository.findByUserId(response.getEmail());
        if (member != null) {
            return member.getId();
        }
        return saveMember(response);
    }

    private Long saveMember(GoogleUserResponse response) {
        String userId = response.getEmail();
        String name = response.getName();
        String password = passwordEncoder.encrypt(oauthProvider.getTemporaryPassword());

        Member savedMember = memberRepository.save(new Member(userId, password, name));
        return savedMember.getId();
    }
}
