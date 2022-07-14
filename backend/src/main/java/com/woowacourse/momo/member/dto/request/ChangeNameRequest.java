package com.woowacourse.momo.member.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameRequest {

    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;
}
