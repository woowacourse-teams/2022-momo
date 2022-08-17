package com.woowacourse.momo.group.domain.group;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.duration.Duration;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Deadline {

    @Column(name = "deadline", nullable = false)
    private LocalDateTime value;

    public Deadline(LocalDateTime value, Duration duration) {
        this.value = value;
        validateFuture();
        validateIsBeforeStartDuration(duration);
    }

    public boolean isOver() {
        return value.isBefore(LocalDateTime.now());
    }

    private void validateFuture() {
        if (isOver()) {
            throw new MomoException(ErrorCode.GROUP_DEADLINE_NOT_PAST);
        }
    }

    private void validateIsBeforeStartDuration(Duration duration) {
        if (duration.isAfterStartDate(value)) {
            throw new MomoException(ErrorCode.GROUP_DURATION_NOT_AFTER_DEADLINE);
        }
    }
}
