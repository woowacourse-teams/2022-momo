package com.woowacourse.momo.group.service.dto.request.calendar;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.calendar.Deadline;

@RequiredArgsConstructor
public class DeadlineRequest {

    private final LocalDateTime deadline;

    public Deadline getDeadline() {
        return new Deadline(deadline);
    }
}
