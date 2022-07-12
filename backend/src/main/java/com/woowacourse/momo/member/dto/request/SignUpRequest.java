package com.woowacourse.momo.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.member.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;

    public Member toMember() {
        return new Member(email, password, name);
    }
}
