package com.woowacourse.momo.group.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.config.Authenticated;
import com.woowacourse.momo.auth.config.AuthenticationPrincipal;
import com.woowacourse.momo.group.controller.param.GroupParam;
import com.woowacourse.momo.group.controller.param.GroupRequestAssembler;
import com.woowacourse.momo.group.service.GroupManageService;
import com.woowacourse.momo.group.service.response.GroupIdResponse;

@Authenticated
@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupHostController {

    private final GroupRequestAssembler assembler;
    private final GroupManageService groupManageService;

    @PostMapping
    public ResponseEntity<GroupIdResponse> create(@AuthenticationPrincipal Long memberId,
                                                  @RequestBody @Valid GroupParam param) {
        GroupIdResponse groupIdResponse = groupManageService.create(memberId, assembler.groupRequest(param));
        return ResponseEntity.created(URI.create("/api/groups/" + groupIdResponse.getGroupId()))
                .body(groupIdResponse);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId,
                                       @RequestBody @Valid GroupParam param) {
        groupManageService.update(memberId, groupId, assembler.groupRequest(param));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/close")
    public ResponseEntity<Void> closeEarly(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupManageService.closeEarly(memberId, groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupManageService.delete(memberId, groupId);
        return ResponseEntity.noContent().build();
    }
}
