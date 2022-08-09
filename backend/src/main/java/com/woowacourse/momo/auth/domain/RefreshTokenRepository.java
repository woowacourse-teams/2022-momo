package com.woowacourse.momo.auth.domain;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface RefreshTokenRepository extends Repository<RefreshToken, Long> {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
