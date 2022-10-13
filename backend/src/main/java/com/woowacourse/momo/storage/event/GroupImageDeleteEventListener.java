package com.woowacourse.momo.storage.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.event.GroupDeleteEvent;
import com.woowacourse.momo.storage.domain.GroupImageRepository;

@RequiredArgsConstructor
@Component
public class GroupImageDeleteEventListener {

    private final GroupImageRepository groupImageRepository;

    @EventListener
    public void deleteGroupImage(GroupDeleteEvent event) {
        groupImageRepository.deleteByGroupId(event.getId());
    }
}
