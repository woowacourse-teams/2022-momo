package com.woowacourse.momo.member.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyInfoResponse {

    private Long id;
    private String userId;
    private String name;
}
