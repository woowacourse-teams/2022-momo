package com.woowacourse.momo.repository;

import com.woowacourse.momo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
