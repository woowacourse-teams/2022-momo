package com.woowacourse.momo.member.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.dto.request.SignUpRequest;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long signUp(SignUpRequest request) {
        Member savedMember = memberRepository.save(request.toMember());
        return savedMember.getId();
    }
}
