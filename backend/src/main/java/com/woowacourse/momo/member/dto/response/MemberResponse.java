package com.woowacourse.momo.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.member.domain.Member;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private long id;
    private String name;

    public static MemberResponse toResponse(Member host) {
        return new MemberResponse(host.getId(), host.getName());
    }
}
