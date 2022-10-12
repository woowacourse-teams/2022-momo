package com.woowacourse.momo.group.domain.participant;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepository extends Repository<Participant, Long> {

    @Modifying
    @Query("delete from Participant p where p.group.id = :groupId")
    void deleteAllByGroupId(@Param("groupId") Long groupId);

    @Modifying
    @Query("delete from Participant p where p.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);
}
