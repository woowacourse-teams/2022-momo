package com.woowacourse.momo.member.service.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ChangeNameRequest {

    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;
}
