package com.woowacourse.momo.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
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

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long id) {
        memberService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
