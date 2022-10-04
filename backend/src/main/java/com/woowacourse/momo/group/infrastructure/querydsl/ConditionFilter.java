package com.woowacourse.momo.group.infrastructure.querydsl;

import static com.woowacourse.momo.group.domain.QGroup.group;

import java.time.LocalDateTime;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.search.SearchCondition;

public class ConditionFilter {

    public BooleanBuilder filterByCondition(SearchCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        filterByCategory(booleanBuilder, condition.getCategory());
        excludeFinished(booleanBuilder, condition.excludeFinished());
        afterNow(booleanBuilder, condition.orderByDeadline());
        containKeyword(booleanBuilder, condition.getKeyword());

        return booleanBuilder;
    }

    private void excludeFinished(BooleanBuilder booleanBuilder, boolean excludeFinished) {
        if (excludeFinished) {
            booleanBuilder.and(afterNow()
                    .and(notClosedEarly()));
        }
    }

    private void filterByCategory(BooleanBuilder booleanBuilder, Long categoryId) {
        if (categoryId != null) {
            Category category = Category.from(categoryId);
            booleanBuilder.and(group.category.eq(category));
        }
    }

    private void containKeyword(BooleanBuilder booleanBuilder, String keyword) {
        if (keyword != null) {
            BooleanExpression nameContains = group.name.value.contains(keyword);
            BooleanExpression descriptionContains = group.description.value.contains(keyword);

            booleanBuilder.and(nameContains.or(descriptionContains));
        }
    }

    private void afterNow(BooleanBuilder booleanBuilder, boolean orderByDeadline) {
        if (orderByDeadline) {
            booleanBuilder.and(afterNow());
        }
    }

    private BooleanExpression notClosedEarly() {
        return group.closedEarly.isFalse();
    }

    private BooleanExpression afterNow() {
        return group.calendar.deadline.value.gt(LocalDateTime.now());
    }
}
