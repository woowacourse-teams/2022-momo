package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_DEADLINE_NOT_PAST;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Deadline {

    @Column(name = "deadline", nullable = false)
    private LocalDateTime value;

    public Deadline(LocalDateTime value) {
        validateDeadlineIsAfterNow(value);
        this.value = value;
    }

    public boolean isOver() {
        return value.isBefore(LocalDateTime.now());
    }

    private void validateDeadlineIsAfterNow(LocalDateTime value) {
        if (isBeforeThanNow(value)) {
            throw new MomoException(GROUP_DEADLINE_NOT_PAST);
        }
    }

    private boolean isBeforeThanNow(LocalDateTime value) {
        return value.isBefore(LocalDateTime.now());
    }
}
