package com.woowacourse.momo.auth.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.dto.SignInRequest;
import com.woowacourse.momo.auth.dto.SignInResponse;
import com.woowacourse.momo.auth.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest request) {
        SignInResponse response = authService.signIn(request);

        return ResponseEntity.ok(response);
    }
}
