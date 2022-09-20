package com.woowacourse.momo.group.infrastructure.querydsl;

import static com.woowacourse.momo.group.domain.QGroup.group;

import java.time.LocalDateTime;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;

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
                    .and(notClosedEarly())
                    .and(isNotParticipantsFull()));
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
            BooleanExpression descriptionContains = group.description.contains(keyword);

            booleanBuilder.and(nameContains.or(descriptionContains));
        }
    }

    private void afterNow(BooleanBuilder booleanBuilder, boolean orderByDeadline) {
        if (orderByDeadline) {
            booleanBuilder.and(afterNow());
        }
    }

    private BooleanExpression isNotParticipantsFull() {
        NumberPath<Integer> capacity = group.participants.capacity.value;

        NumberExpression<Integer> sizeWithoutHost = group.participants.participants.size();
        Expression<Integer> hostSize = Expressions.constant(1);
        NumberExpression<Integer> participantsSize = sizeWithoutHost.add(hostSize);

        return capacity.gt(participantsSize);
    }

    private BooleanExpression notClosedEarly() {
        return group.closedEarly.isFalse();
    }

    private BooleanExpression afterNow() {
        return group.calendar.deadline.value.gt(LocalDateTime.now());
    }
}
