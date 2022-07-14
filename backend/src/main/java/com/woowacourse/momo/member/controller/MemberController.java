package com.woowacourse.momo.member.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.auth.dto.request.LoginRequest;
import com.woowacourse.momo.member.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.dto.response.MemberResponse;
import com.woowacourse.momo.member.service.MemberService;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> find(@AuthenticationPrincipal Long id) {
        MemberResponse response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Long id,
                                               @RequestBody @Valid ChangePasswordRequest request) {
        memberService.updatePassword(id, request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/name")
    public ResponseEntity<Void> updateName(@AuthenticationPrincipal Long id,
                                           @RequestBody @Valid ChangeNameRequest request) {
        memberService.updateName(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long id) {
        memberService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
