package com.woowacourse.momo.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberFindService {

    private final MemberRepository memberRepository;

    public Member findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
        validateExistMember(member);
        return member;
    }

    public Member findByUserIdAndPassword(UserId userId, Password password) {
        Member member = memberRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_INVALID_ID_AND_PASSWORD));
        validateExistMember(member);
        return member;
    }

    private void validateExistMember(Member member) {
        if (member.isDeleted()) {
            throw new MemberException(MemberErrorCode.MEMBER_DELETED);
        }
    }
}
