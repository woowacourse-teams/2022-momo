package com.woowacourse.momo.group.repository;

import static com.woowacourse.momo.group.domain.QGroup.group;
import static com.woowacourse.momo.group.domain.participant.QParticipant.participant;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.participant.Participant;
import com.woowacourse.momo.member.domain.Member;

@Repository
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GroupRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Group> findGroups(FindCondition condition, Pageable pageable) {
        return findGroups(condition, pageable, () -> null);
    }

    @Override
    public Page<Group> findHostedGroups(FindCondition condition, Member member, Pageable pageable) {
        return findGroups(condition, pageable, () -> isHost(member));
    }

    @Override
    public Page<Group> findParticipatedGroups(FindCondition condition, Member member, Pageable pageable) {
        return findGroups(condition, pageable, () -> isParticipated(member));
    }

    private Page<Group> findGroups(FindCondition condition, Pageable pageable, Supplier<BooleanExpression> supplier) {
        List<Group> groups = queryFactory
                .selectFrom(group)
                .where(
                        supplier.get(),
                        filterByCondition(condition)
                )
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(groups, pageable, groups::size);
    }

    @Override
    public List<Group> findParticipatedGroups(Member member) {
        return queryFactory
                .selectFrom(group)
                .where(isParticipated(member))
                .fetch();
    }

    private BooleanExpression isHost(Member member) {
        return group.participants.host.eq(member);
    }

    private BooleanExpression isParticipated(Member member) {
        return isHost(member).or(isParticipant(member));
    }

    private BooleanExpression isParticipant(Member member) {
        JPQLQuery<Participant> targetMember = JPAExpressions
                .selectFrom(participant)
                .where(participant.member.eq(member));

        return group.participants.participants.contains(targetMember);
    }

    private BooleanBuilder filterByCondition(FindCondition condition) {
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

    private List<OrderSpecifier<?>> orderByDeadlineAsc(boolean orderByDeadline) {
        List<OrderSpecifier<?>> orderBy = new LinkedList<>();

        if (orderByDeadline) {
            orderBy.add(group.calendar.deadline.value.asc());
        }

        orderBy.add(orderByIdDesc());

        return orderBy;
    }

    private OrderSpecifier<Long> orderByIdDesc() {
        return group.id.desc();
    }
}
