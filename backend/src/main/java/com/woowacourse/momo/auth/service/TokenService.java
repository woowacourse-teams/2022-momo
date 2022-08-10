package com.woowacourse.momo.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.domain.Token;
import com.woowacourse.momo.auth.domain.TokenRepository;
import com.woowacourse.momo.member.domain.Member;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public void synchronizeRefreshToken(Member member, String refreshToken) {
        Optional<Token> token = tokenRepository.findByMemberId(member.getId());
        if (token.isPresent()) {
            token.get().updateRefreshToken(refreshToken);
            return;
        }
        tokenRepository.save(new Token(member, refreshToken));
    }
}
