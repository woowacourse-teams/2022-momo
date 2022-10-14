package com.woowacourse.momo.group.domain.participant;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.woowacourse.momo.group.domain.Group;

public interface ParticipantRepository extends Repository<Participant, Long> {

    @Modifying
    @Query("delete from Participant p where p.group.id = :groupId")
    void deleteAllByGroupId(@Param("groupId") Long groupId);

    @Modifying
    @Query("delete from Participant p where p.member.id = :memberId and p.group in (:groups)")
    void deleteAllByMemberIdInGroups(@Param("memberId") Long memberId, @Param("groups") List<Group> groups);
}
