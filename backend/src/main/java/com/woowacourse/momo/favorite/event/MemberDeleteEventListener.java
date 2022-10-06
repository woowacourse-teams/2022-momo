package com.woowacourse.momo.favorite.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.favorite.domain.FavoriteRepository;
import com.woowacourse.momo.member.event.MemberDeleteEvent;

@RequiredArgsConstructor
@Component
public class MemberDeleteEventListener implements ApplicationListener<MemberDeleteEvent> {

    private final FavoriteRepository favoriteRepository;

    @Override
    public void onApplicationEvent(MemberDeleteEvent event) {
        favoriteRepository.deleteAllByMemberId(event.getId());
    }
}
