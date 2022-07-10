package com.woowacourse.momo.service.dto.response.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private String day;
    private TimeResponse time;
}
