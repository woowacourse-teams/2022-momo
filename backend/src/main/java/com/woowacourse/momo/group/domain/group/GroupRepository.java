package com.woowacourse.momo.group.domain.group;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findAll(Pageable pageable);

    @Query("select distinct g from Group g join Participant p on g = p.group where p.member.id = :memberId")
    List<Group> findParticipatedGroups(@Param("memberId") Long memberId);
}
