package com.woowacourse.momo.auth.support.google.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponse {

    private String name;
    private String email;
}
