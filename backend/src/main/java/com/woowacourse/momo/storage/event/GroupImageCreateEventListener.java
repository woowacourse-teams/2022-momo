package com.woowacourse.momo.storage.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.event.GroupCreateEvent;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;

@RequiredArgsConstructor
@Component
public class GroupImageCreateEventListener {

    private final GroupImageRepository groupImageRepository;

    @EventListener
    public void createGroupImage(GroupCreateEvent event) {
        GroupImage groupImage = new GroupImage(event.getGroupId(), event.getCategory().getDefaultImageName());

        groupImageRepository.save(groupImage);
    }
}
