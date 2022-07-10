package com.woowacourse.momo.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private long id;
    private String name;

    public static MemberResponse toResponse(Member host) {
        return new MemberResponse(host.getId(), host.getName());
    }
}
