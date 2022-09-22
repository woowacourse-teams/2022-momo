package com.woowacourse.momo.group.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationResponse {

    private String address;
    private String buildingName;
    private String detail;
}
