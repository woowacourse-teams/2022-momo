package com.woowacourse.momo.group.domain.schedule;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface ScheduleRepository extends Repository<Schedule, Long> {

    List<Schedule> findByGroupId(Long groupId);
}
