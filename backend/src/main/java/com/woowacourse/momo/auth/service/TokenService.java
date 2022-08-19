package com.woowacourse.momo.auth.service;

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

    @Transactional
    public void synchronizeRefreshToken(Member member, String refreshToken) {
        tokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        token -> token.updateRefreshToken(refreshToken),
                        () -> tokenRepository.save(new Token(member, refreshToken))
                );
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        tokenRepository.deleteByMemberId(memberId);
    }
}
