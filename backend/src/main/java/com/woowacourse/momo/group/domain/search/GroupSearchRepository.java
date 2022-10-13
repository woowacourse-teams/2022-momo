package com.woowacourse.momo.group.domain.search;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

public interface GroupSearchRepository extends Repository<Group, Long>, GroupSearchRepositoryCustom {

    Optional<Group> findById(Long id);

    @Query("select g from Group g "
            + "left join fetch g.calendar.schedules "
            + "join fetch g.participants.host "
            + "where g.id = :id")
    Optional<Group> findByIdWithHostAndSchedule(Long id);

    @Query("SELECT g FROM Group g "
            + "WHERE g.participants.host = :member "
            + "OR ( :member IN (SELECT p.member.id FROM Participant p WHERE p.group = g) )")
    List<Group> findParticipatedGroups(Member member);

    boolean existsById(Long id);
}
