package com.woowacourse.momo.group.controller.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LocationApiRequest {

    @NotNull
    private String address;

    @NotNull
    private String buildingName;

    @NotNull
    private String detail;
}
