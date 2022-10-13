package com.woowacourse.momo.storage.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.event.GroupCreateEvent;
import com.woowacourse.momo.storage.service.GroupImageService;

@RequiredArgsConstructor
@Component
public class GroupImageCreateEventListener {

    private final GroupImageService groupImageService;

    @EventListener
    public void createGroupImage(GroupCreateEvent event) {
        groupImageService.init(event.getGroupId(), event.getCategory().getDefaultImageName());
    }
}
