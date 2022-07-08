package com.woowacourse.momo.controller;

import com.woowacourse.momo.service.GroupService;
import com.woowacourse.momo.service.dto.request.GroupRequest;
import com.woowacourse.momo.service.dto.request.GroupUpdateRequest;
import com.woowacourse.momo.service.dto.response.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/groups")
@RestController
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody GroupRequest groupRequest) {
        long groupId = groupService.create(groupRequest);
        return ResponseEntity.created(URI.create("/api/groups/" + groupId)).build();
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> findById(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.findById(groupId));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> update(@PathVariable Long groupId, @RequestBody GroupUpdateRequest groupUpdateRequest) {
        groupService.update(groupId, groupUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@PathVariable Long groupId) {
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }
}
