package com.woowacourse.momo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowacourse.momo.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
