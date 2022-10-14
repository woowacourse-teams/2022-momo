package com.woowacourse.momo.category.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.exception.CategoryErrorCode;
import com.woowacourse.momo.category.exception.CategoryException;

@Getter
@RequiredArgsConstructor
public enum Category {

     STUDY(1, "스터디", "study.jpg"),
     MOCO(2, "모각코", "moco.jpg"),
     EAT(3, "식사", "eat.jpg"),
     CAFE(4, "카페", "cafe.jpg"),
     DRINK(5, "술", "drink.jpg"),
     HEALTH(6, "운동", "health.jpg"),
     GAME(7, "게임", "game.jpg"),
     TRAVEL(8, "여행", "travel.jpg"),
     CULTURE(9, "문화생활", "culture.jpg"),
     ETC(10, "기타", "etc.jpg");

    private final long id;
    private final String name;
    private final String defaultImageName;

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

    public String getDefaultImageName() {
        return defaultImageName;
    }
}
