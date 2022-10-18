package com.woowacourse.momo.category.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.exception.CategoryErrorCode;
import com.woowacourse.momo.category.exception.CategoryException;

@Getter
@RequiredArgsConstructor
public enum Category {

    STUDY(1, "스터디", "thumbnail_study.jpg", "icon_study.svg"),
    MOCO(2, "모각코", "thumbnail_moco.jpg", "icon_moco.svg"),
    EAT(3, "식사", "thumbnail_eat.jpg", "icon_eat.svg"),
    CAFE(4, "카페", "thumbnail_cafe.jpg", "icon_cafe.svg"),
    DRINK(5, "술", "thumbnail_drink.jpg", "icon_drink.svg"),
    HEALTH(6, "운동", "thumbnail_health.jpg", "icon_health.svg"),
    GAME(7, "게임", "thumbnail_game.jpg", "icon_game.svg"),
    TRAVEL(8, "여행", "thumbnail_travel.jpg", "icon_travel.svg"),
    CULTURE(9, "문화생활", "thumbnail_culture.jpg", "icon_culture.svg"),
    ETC(10, "기타", "thumbnail_etc.jpg", "icon_etc.svg");

    private final long id;
    private final String name;
    private final String defaultImageName;
    private final String iconName;

    public static Category from(long id) {
        return Arrays.stream(values())
                .filter(category -> category.id == id)
                .findFirst()
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_EXIST));
    }

    public boolean isDefaultImage(String imageName) {
        return defaultImageName.equals(imageName);
    }
}
