package com.woowacourse.momo.auth.support;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.woowacourse.momo.globalException.exception.ErrorCode;
import com.woowacourse.momo.globalException.exception.MomoException;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") final long tokenValidityInMilliseconds,
                            @Value("${security.jwt.token.expire-length}") final long refreshTokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    public String createToken(Long payload) {
        Claims claims = Jwts.claims().setSubject(Long.toString(payload));
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getPayload(String token) {
        return Long.valueOf(
                getClaims(token).getBody().getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new MomoException(ErrorCode.AUTH_EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}

