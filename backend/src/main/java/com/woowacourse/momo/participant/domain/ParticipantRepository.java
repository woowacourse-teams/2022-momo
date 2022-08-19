package com.woowacourse.momo.participant.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

	@Modifying
	@Query("delete from Participant p where p.group.id = :groupId and p.member.id = :memberId")
	void deleteByGroupIdAndMemberId(@Param("groupId") Long groupId, @Param("memberId") Long memberId);
}
