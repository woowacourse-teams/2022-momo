package com.woowacourse.momo.member.controller;


import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.member.dto.request.SignUpRequest;
import com.woowacourse.momo.member.dto.response.MemberResponse;
import com.woowacourse.momo.member.service.MemberService;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        Long id = memberService.signUp(request);

        return ResponseEntity.created(URI.create("/api/members/" + id)).build();
    }

    @GetMapping("/info")
    public ResponseEntity<MemberResponse> find(@AuthenticationPrincipal Long id) {
        MemberResponse response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long id) {
        memberService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
