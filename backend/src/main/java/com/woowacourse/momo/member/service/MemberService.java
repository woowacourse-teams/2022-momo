package com.woowacourse.momo.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.exception.NotFoundGroupException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.member.domain.PasswordEncoder;
import com.woowacourse.momo.member.dto.request.SignUpRequest;
import com.woowacourse.momo.member.dto.response.MemberResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(SignUpRequest request) {
        String password = passwordEncoder.encrypt(request.getPassword());
        Member member = new Member(request.getEmail(), password, request.getName());
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundGroupException::new);

        return MemberResponse.toResponse(member);
    }
}
