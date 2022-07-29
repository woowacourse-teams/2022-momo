package com.woowacourse.momo.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.support.JwtTokenProvider;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider JwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        String password = passwordEncoder.encrypt(request.getPassword());
        Member member = memberRepository.findByUserIdAndPassword(request.getUserId(), password)
                .orElseThrow(() -> new AuthFailException("로그인에 실패했습니다."));
        String token = JwtTokenProvider.createToken(member.getId());

        return new LoginResponse(token);
    }

    @Transactional
    public Long signUp(SignUpRequest request) {
        validateUserId(request.getUserId());
        String password = passwordEncoder.encrypt(request.getPassword());
        Member member = new Member(request.getUserId(), password, request.getName());
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void validateUserId(String userId) {
        if (userId.contains("@")) {
            throw new IllegalArgumentException("잘못된 아이디 형식입니다.");
        }
    }
}
