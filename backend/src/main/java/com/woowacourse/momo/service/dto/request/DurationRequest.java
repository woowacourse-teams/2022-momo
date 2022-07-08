package com.woowacourse.momo.service.dto.request;

import com.woowacourse.momo.domain.group.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DurationRequest {

    @DateTimeFormat
    private LocalDate start;
    @DateTimeFormat
    private LocalDate end;

    public Duration toEntity() {
        return new Duration(start, end);
    }
}
