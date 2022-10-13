package com.woowacourse.momo.group.infrastructure.querydsl;

import static com.woowacourse.momo.favorite.domain.QFavorite.favorite;
import static com.woowacourse.momo.group.domain.QGroup.group;
import static com.woowacourse.momo.group.domain.participant.QParticipant.participant;
import static com.woowacourse.momo.member.domain.QMember.member;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.search.GroupSearchRepositoryCustom;
import com.woowacourse.momo.group.domain.search.SearchCondition;
import com.woowacourse.momo.group.domain.search.dto.GroupSummaryRepositoryResponse;
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
    public Page<GroupSummaryRepositoryResponse> findGroups(SearchCondition condition, Pageable pageable) {
        return findGroups(condition, pageable, () -> null);
    }

    @Override
    public Page<GroupSummaryRepositoryResponse> findHostedGroups(SearchCondition condition, Member member,
                                                                 Pageable pageable) {
        return findGroups(condition, pageable, () -> isHost(member));
    }

    @Override
    public Page<GroupSummaryRepositoryResponse> findParticipatedGroups(SearchCondition condition, Member member,
                                                                       Pageable pageable) {
        return findGroups(condition, pageable, () -> isParticipated(member));
    }

    @Override
    public Page<GroupSummaryRepositoryResponse> findLikedGroups(SearchCondition condition, Long memberId,
                                                                Pageable pageable) {
        List<Long> likedGroupIds = findLikedGroupIds(condition, memberId, pageable);

        List<GroupSummaryRepositoryResponse> groups = queryFactory
                .select(makeProjections()).distinct()
                .from(group)
                .innerJoin(group.participants.host, member)
                .leftJoin(group.participants.participants, participant)
                .innerJoin(favorite).on(group.id.eq(favorite.groupId))
                .fetchJoin()
                .where(group.id.in(likedGroupIds))
                .groupBy(group.id)
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(group.count())
                .from(group)
                .leftJoin(group.participants.participants, participant)
                .innerJoin(favorite).on(group.id.eq(favorite.groupId))
                .where(
                        favorite.memberId.eq(memberId),
                        conditionFilter.filterByCondition(condition)
                );

        return PageableExecutionUtils.getPage(groups, pageable, countQuery::fetchOne);
    }

    private List<Long> findLikedGroupIds(SearchCondition condition, Long memberId, Pageable pageable) {
        return queryFactory
                .select(group.id)
                .from(group)
                .innerJoin(favorite).on(group.id.eq(favorite.groupId))
                .where(
                        favorite.memberId.eq(memberId),
                        conditionFilter.filterByCondition(condition)
                )
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Page<GroupSummaryRepositoryResponse> findGroups(SearchCondition condition, Pageable pageable,
                                                            Supplier<BooleanExpression> mainCondition) {
        List<GroupSummaryRepositoryResponse> groups = queryFactory
                .select(makeProjections())
                .from(group)
                .innerJoin(group.participants.host, member)
                .leftJoin(group.participants.participants, participant)
                .where(
                        mainCondition.get(),
                        conditionFilter.filterByCondition(condition)
                )
                .groupBy(group.id)
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

    private static ConstructorExpression<GroupSummaryRepositoryResponse> makeProjections() {
        int host = 1;
        return Projections.constructor(GroupSummaryRepositoryResponse.class,
                group.id,
                group.name.value,
                group.participants.host.id,
                member.userName.value,
                group.category,
                group.participants.capacity.value,
                participant.count().intValue().add(host),
                group.closedEarly,
                group.calendar.deadline.value
        );
    }

    @Override
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
