package com.woowacourse.momo.service.dto.response.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.domain.member.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseAssembler {

    public static MemberResponse memberResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
