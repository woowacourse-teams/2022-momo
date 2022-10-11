package com.woowacourse.momo.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserIdAndPassword(UserId userId, Password password);

    Optional<Member> findByUserId(UserId userId);

    boolean existsByUserId(UserId userId);

    boolean existsByUserName(UserName userName);
}
