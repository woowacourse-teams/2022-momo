package com.woowacourse.momo.category.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.exception.CategoryErrorCode;
import com.woowacourse.momo.category.exception.CategoryException;

@Getter
@RequiredArgsConstructor
public enum Category {

     STUDY(1, "스터디", "study.jpg", "study.png"),
     MOCO(2, "모각코", "moco.jpg", "moco.png"),
     EAT(3, "식사", "eat.jpg", "eat.png"),
     CAFE(4, "카페", "cafe.jpg", "cafe.png"),
     DRINK(5, "술", "drink.jpg", "drink.png"),
     HEALTH(6, "운동", "health.jpg", "health.png"),
     GAME(7, "게임", "game.jpg", "game.png"),
     TRAVEL(8, "여행", "travel.jpg", "travel.png"),
     CULTURE(9, "문화생활", "culture.jpg", "culture.png"),
     ETC(10, "기타", "etc.jpg", "etc.png");

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
        return Arrays.stream(values())
                .anyMatch(category -> category.defaultImageName.equals(imageName));
    }
}
