package com.woowacourse.momo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
