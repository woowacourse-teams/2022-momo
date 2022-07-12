package com.woowacourse.momo.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.dto.request.SignUpRequest;
import com.woowacourse.momo.member.dto.response.MemberResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(SignUpRequest request) {
        Member savedMember = memberRepository.save(request.toMember());
        return savedMember.getId();
    }

    public MemberResponse findById(Long id) {
        // Long? dto?
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundGroupException::new);

        return MemberResponse.toResponse(member);
    }
}
