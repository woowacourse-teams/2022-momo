package com.woowacourse.momo.group.domain.group;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.woowacourse.momo.category.domain.Category;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    Page<Group> findAll(Pageable pageable);

    Page<Group> findAllByCategory(Category category, Pageable pageable);

    @Query("select distinct g from Group g join Participant p on g = p.group where p.member.id = :memberId")
    List<Group> findParticipatedGroups(Long memberId);

    @Query("select g from Group g where g.name like CONCAT('%',:keyword,'%')")
    Page<Group> findAllByKeyword(String keyword, Pageable pageable);
}
