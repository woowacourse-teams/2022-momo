package com.woowacourse.momo.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByGroupIdAndMemberId(Long groupId, Long memberId);

    Optional<Favorite> findByGroupIdAndMemberId(Long groupId, Long memberId);

    List<Favorite> findAllByMemberId(Long memberId);
}
