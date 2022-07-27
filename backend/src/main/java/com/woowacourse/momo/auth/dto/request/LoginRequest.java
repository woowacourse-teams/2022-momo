package com.woowacourse.momo.auth.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    private String email;
    @NotBlank(message = "패스워드는 빈 값일 수 없습니다.")
    private String password;
}
