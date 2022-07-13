package com.woowacourse.momo.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.member.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpRequest {

    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    private String email;
    @NotBlank(message = "패스워드는 빈 값일 수 없습니다.")
    private String password;
    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;
}
