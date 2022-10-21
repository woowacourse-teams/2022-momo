package com.woowacourse.momo.group.domain.search;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

public interface GroupSearchRepository extends Repository<Group, Long>, GroupSearchRepositoryCustom {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Group> findById(Long id);

    @Query("select g from Group g "
            + "left join fetch g.calendar.schedules "
            + "join fetch g.participants.host "
            + "where g.id = :id")
    Optional<Group> findByIdWithHostAndSchedule(@Param("id") Long id);

    @Query("SELECT g FROM Group g "
            + "WHERE g.participants.host = :member "
            + "OR ( :member IN (SELECT p.member.id FROM Participant p WHERE p.group = g) )")
    List<Group> findParticipatedGroups(@Param("member") Member member);

    boolean existsById(Long id);
}
