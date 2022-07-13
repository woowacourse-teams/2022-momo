package com.woowacourse.momo.auth.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.dto.LoginRequest;
import com.woowacourse.momo.auth.dto.LoginResponse;
import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.member.domain.PasswordEncoder;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider JwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        String password = passwordEncoder.encrypt(request.getPassword());
        Member member = memberRepository.findByEmailAndPassword(request.getEmail(), password)
                .orElseThrow(() -> new AuthFailException("로그인에 실패했습니다."));
        String token = JwtTokenProvider.createToken(member.getId());

        return new LoginResponse(token);
    }
}
