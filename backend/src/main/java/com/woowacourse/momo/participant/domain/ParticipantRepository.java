package com.woowacourse.momo.participant.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

	void deleteByGroupIdAndMemberId(Long groupId, Long memberId);
}
