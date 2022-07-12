package com.woowacourse.momo.auth.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.dto.SignInRequest;
import com.woowacourse.momo.auth.dto.SignInResponse;
import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider JwtTokenProvider;

    public SignInResponse signIn(SignInRequest request) {
        // 암호화 추가 위치
        Member member = memberRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new AuthFailException("로그인에 실패했습니다."));
        String token = JwtTokenProvider.createToken(member.getId());

        return new SignInResponse(token);
    }

}
