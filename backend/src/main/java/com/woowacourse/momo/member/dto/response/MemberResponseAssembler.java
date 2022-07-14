package com.woowacourse.momo.member.dto.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseAssembler {

    public static MemberResponse memberResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}