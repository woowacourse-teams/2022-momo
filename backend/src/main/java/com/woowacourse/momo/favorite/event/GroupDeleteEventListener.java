package com.woowacourse.momo.favorite.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.event.GroupDeleteEvent;

@RequiredArgsConstructor
@Component
public class GroupDeleteEventListener implements ApplicationListener<GroupDeleteEvent> {

    private final FavoriteRepository favoriteRepository;

    @Override
    public void onApplicationEvent(GroupDeleteEvent event) {
        favoriteRepository.deleteAllByGroupId(event.getId());
    }
}
