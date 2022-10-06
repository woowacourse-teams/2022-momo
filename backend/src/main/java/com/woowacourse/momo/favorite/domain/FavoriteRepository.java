package com.woowacourse.momo.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);

    Optional<Favorite> findByGroupIdAndMemberId(Long groupId, Long memberId);

    List<Favorite> findAllByMemberId(Long memberId);

    void delete(Favorite favorite);

    void deleteAllByGroupId(Long groupId);

    void deleteAllByMemberId(Long memberId);
}
