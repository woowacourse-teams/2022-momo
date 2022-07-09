package com.woowacourse.momo.service.dto.response;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.domain.group.Duration;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DurationResponse {

    @DateTimeFormat
    private LocalDate start;
    @DateTimeFormat
    private LocalDate end;

    public static DurationResponse toResponse(Duration duration) {
        return new DurationResponse(duration.getStartDate(), duration.getEndDate());
    }
}
