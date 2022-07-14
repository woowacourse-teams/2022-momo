package com.woowacourse.momo.category.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

import lombok.Getter;

@Getter
public enum Category {

    STUDY(1, "스터디"),
    MOCO(2, "모각코"),
    EAT(3, "식사"),
    CAFE(4, "카페"),
    DRINK(5, "술"),
    HEALTH(6, "운동"),
    GAME(7, "게임"),
    TRAVEL(8, "여행"),
    ETC(9, "기타");

    private final long id;
    private final String name;

    Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category from(long id) {
        return Arrays.stream(values())
                .filter(category -> category.id == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다."));
    }
}
