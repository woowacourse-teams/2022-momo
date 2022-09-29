package com.woowacourse.momo.group.infrastructure.querydsl;

import static com.woowacourse.momo.group.domain.QGroup.group;
import static com.woowacourse.momo.group.domain.favorite.QFavorite.favorite;
import static com.woowacourse.momo.group.domain.participant.QParticipant.participant;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.search.GroupSearchRepositoryCustom;
import com.woowacourse.momo.group.domain.search.SearchCondition;
import com.woowacourse.momo.member.domain.Member;

@Repository
public class GroupSearchRepositoryImpl implements GroupSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final ConditionFilter conditionFilter;

    public GroupSearchRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.conditionFilter = new ConditionFilter();
    }

    @Override
    public Page<Group> findGroups(SearchCondition condition, Pageable pageable) {
        return findGroups(condition, pageable, () -> null);
    }

    @Override
    public Page<Group> findHostedGroups(SearchCondition condition, Member member, Pageable pageable) {
        return findGroups(condition, pageable, () -> isHost(member));
    }

    @Override
    public Page<Group> findParticipatedGroups(SearchCondition condition, Member member, Pageable pageable) {
        return findGroups(condition, pageable, () -> isParticipated(member));
    }

    @Override
    public Page<Group> findLikedGroups(SearchCondition condition, Member member, Pageable pageable) {
        List<Group> groups = queryFactory
                .selectFrom(group)
                .leftJoin(group.participants.participants, participant)
                .innerJoin(group.favorites.favorites, favorite)
                .fetchJoin()
                .where(
                        group.id.in(findLikedGroupIds(condition, member, pageable))
                )
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .fetch();

        return PageableExecutionUtils.getPage(groups, pageable, groups::size);
    }

    private List<Long> findLikedGroupIds(SearchCondition condition, Member member, Pageable pageable) {
        return queryFactory
                .select(group.id)
                .from(group)
                .leftJoin(group.participants.participants, participant)
                .innerJoin(group.favorites.favorites, favorite)
                .where(
                        favorite.member.eq(member),
                        conditionFilter.filterByCondition(condition)
                )
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Page<Group> findGroups(SearchCondition condition, Pageable pageable,
                                   Supplier<BooleanExpression> mainCondition) {
        List<Group> groups = queryFactory
                .select(group).distinct()
                .from(group)
                .leftJoin(group.participants.participants, participant)
                .where(
                        mainCondition.get(),
                        conditionFilter.filterByCondition(condition)
                )
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(group.countDistinct())
                .from(group)
                .leftJoin(group.participants.participants, participant)
                .where(
                        mainCondition.get(),
                        conditionFilter.filterByCondition(condition)
                );

        return PageableExecutionUtils.getPage(groups, pageable, countQuery::fetchOne);
    }

    public List<Group> findParticipatedGroups(Member member) {
        return queryFactory
                .select(group).distinct()
                .from(group)
                .leftJoin(group.participants.participants, participant)
                .where(isParticipated(member))
                .fetch();
    }

    private BooleanExpression isHost(Member member) {
        return group.participants.host.eq(member);
    }

    private BooleanExpression isParticipated(Member member) {
        return isHost(member).or(participant.member.eq(member));
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
