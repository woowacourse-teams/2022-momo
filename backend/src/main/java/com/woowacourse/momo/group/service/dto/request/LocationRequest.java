package com.woowacourse.momo.group.service.dto.request;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.Location;

@RequiredArgsConstructor
public class LocationRequest {

    private final String address;
    private final String buildingName;
    private final String detail;

    public Location getLocation() {
        return new Location(address, buildingName, detail);
    }
}
