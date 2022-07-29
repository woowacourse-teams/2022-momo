package com.woowacourse.momo.auth.service.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "아이디는 빈 값일 수 없습니다.")
    private String userId;
    @NotBlank(message = "패스워드는 빈 값일 수 없습니다.")
    private String password;
}
