package com.woowacourse.momo.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateExistMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));

        if (member.isDeleted()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_EXIST);
        }
    }
}
