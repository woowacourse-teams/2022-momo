package com.woowacourse.momo.fixture;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.group.controller.dto.request.LocationApiRequest;
import com.woowacourse.momo.group.domain.Location;
import com.woowacourse.momo.group.service.dto.request.LocationRequest;

@SuppressWarnings("NonAsciiCharacters")
@Getter
@AllArgsConstructor
public enum LocationFixture {

    잠실캠퍼스("서울 송파구 올림픽로 35다길 42", "루터회관", "13층"),
    선릉캠퍼스("서울 강남구 테헤란로 411", "성담빌딩", "14층"),
    잠실역_스타벅스("서울 송파구 올림픽로 289", "시그마타워", "1층"),
    선릉역_스타벅스("서울 강남구 테헤란로 334", "LG화재빌딩", "1층"),
    ;

    private final String address;
    private final String buildingName;
    private final String detail;

    public Location toLocation() {
        return new Location(address, buildingName, detail);
    }

    public LocationRequest toRequest() {
        return new LocationRequest(address, buildingName, detail);
    }

    public LocationApiRequest toApiRequest() {
        return new LocationApiRequest(address, buildingName, detail);
    }
}
