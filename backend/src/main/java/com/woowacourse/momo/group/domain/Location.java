package com.woowacourse.momo.group.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Location {

    @Column(name = "locationAddress", nullable = false)
    private String address;

    @Column(name = "locationBuildingName", nullable = false)
    private String buildingName;

    @Column(name = "locationDetail", nullable = false)
    private String detail;
}
