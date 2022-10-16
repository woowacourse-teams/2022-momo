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
import com.woowacourse.momo.group.controller.dto.request.GroupApiRequest;
import com.woowacourse.momo.group.controller.dto.request.GroupRequestAssembler;
import com.woowacourse.momo.group.service.GroupModifyService;
import com.woowacourse.momo.group.service.dto.response.GroupIdResponse;

@Authenticated
@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupModifyController {

    private final GroupRequestAssembler assembler;
    private final GroupModifyService groupModifyService;

    @PostMapping
    public ResponseEntity<GroupIdResponse> create(@AuthenticationPrincipal Long memberId,
                                                  @RequestBody @Valid GroupApiRequest request) {
        GroupIdResponse response = groupModifyService.create(memberId, assembler.groupRequest(request));
        URI uri = URI.create("/api/groups/" + response.getGroupId());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId,
                                       @RequestBody @Valid GroupApiRequest request) {
        groupModifyService.update(memberId, groupId, assembler.groupRequest(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/close")
    public ResponseEntity<Void> closeEarly(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupModifyService.closeEarly(memberId, groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long memberId, @PathVariable Long groupId) {
        groupModifyService.delete(memberId, groupId);
        return ResponseEntity.noContent().build();
    }
}
