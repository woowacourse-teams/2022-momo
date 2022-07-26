package com.woowacourse.momo.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ChangePasswordRequest {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

    @Pattern(regexp = PASSWORD_PATTERN,
            message = "패스워드는 영문자와 하나 이상의 숫자, 특수 문자를 갖고 있어야 합니다.")
    @NotBlank(message = "패스워드는 빈 값일 수 없습니다.")
    private String password;
}
