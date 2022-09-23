package com.woowacourse.momo.auth.domain;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface TokenRepository extends Repository<Token, Long> {

    Token save(Token token);

    Optional<Token> findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
