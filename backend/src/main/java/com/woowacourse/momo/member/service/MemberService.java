package com.woowacourse.momo.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.dto.response.MemberResponseAssembler;
import com.woowacourse.momo.member.dto.response.MyInfoResponse;
import com.woowacourse.momo.member.exception.NotFoundMemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MyInfoResponse findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);

        return MemberResponseAssembler.myInfoResponse(member);
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void updatePassword(Long id, ChangePasswordRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);

        String encryptedPassword = passwordEncoder.encrypt(request.getPassword());
        member.changePassword(encryptedPassword);
    }

    @Transactional
    public void updateName(Long id, ChangeNameRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);

        member.changeName(request.getName());
    }
}
