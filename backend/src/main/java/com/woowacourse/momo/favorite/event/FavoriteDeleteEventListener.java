package com.woowacourse.momo.favorite.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.group.event.GroupDeleteEvent;
import com.woowacourse.momo.member.event.MemberDeleteEvent;

@RequiredArgsConstructor
@Transactional
@Component
public class FavoriteDeleteEventListener {

    private final FavoriteRepository favoriteRepository;

    @EventListener
    @Async
    public void deleteGroup(GroupDeleteEvent event) {
        favoriteRepository.deleteAllByGroupId(event.getId());
    }

    @EventListener
    @Async
    public void deleteMember(MemberDeleteEvent event) {
        favoriteRepository.deleteAllByMemberId(event.getId());
    }
}
