package com.woowacourse.momo.group.repository;

import static com.woowacourse.momo.group.domain.QGroup.group;

import java.time.LocalDateTime;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;

import com.woowacourse.momo.category.domain.Category;

public class ConditionFilter {

    public BooleanBuilder filterByCondition(FindCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        excludeFinished(booleanBuilder, condition.excludeFinished());
        filterByCategory(booleanBuilder, condition.getCategory());
        containKeyword(booleanBuilder, condition.getKeyword());
        afterNow(booleanBuilder, condition.orderByDeadline());

        return booleanBuilder;
    }

    private void excludeFinished(BooleanBuilder booleanBuilder, boolean condition) {
        if (condition) {
            booleanBuilder.and(afterNow()
                    .and(notClosedEarly())
                    .and(isNotParticipantsFull()));
        }
    }

    private void filterByCategory(BooleanBuilder booleanBuilder, Optional<Long> condition) {
        condition.ifPresent(categoryId -> {
            Category category = Category.from(categoryId);
            booleanBuilder.and(group.category.eq(category));
        });
    }

    private void containKeyword(BooleanBuilder booleanBuilder, Optional<String> condition) {
        condition.ifPresent(keyword -> {
            BooleanExpression nameContains = group.name.value.contains(keyword);
            BooleanExpression descriptionContains = group.description.contains(keyword);

            booleanBuilder.and(nameContains.or(descriptionContains));
        });
    }

    private void afterNow(BooleanBuilder booleanBuilder, boolean condition) {
        if (condition) {
            booleanBuilder.and(afterNow());
        }
    }

    private BooleanExpression isNotParticipantsFull() {
        NumberPath<Integer> capacity = group.participants.capacity.value;
        NumberExpression<Integer> participantsSize = group.participants.participants.size()
                .add(Expressions.constant(1));

        return capacity.gt(participantsSize);
    }

    private BooleanExpression notClosedEarly() {
        return group.closedEarly.isFalse();
    }

    private BooleanExpression afterNow() {
        return group.calendar.deadline.value.gt(LocalDateTime.now());
    }
}
