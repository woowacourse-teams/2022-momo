package com.woowacourse.momo.member.service.dto.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.member.domain.member.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseAssembler {

    public static MemberResponse memberResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
