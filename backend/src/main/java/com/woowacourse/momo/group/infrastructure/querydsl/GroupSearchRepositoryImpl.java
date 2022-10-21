package com.woowacourse.momo.group.infrastructure.querydsl;

import static com.woowacourse.momo.favorite.domain.QFavorite.favorite;
import static com.woowacourse.momo.group.domain.QGroup.group;
import static com.woowacourse.momo.group.domain.participant.QParticipant.participant;
import static com.woowacourse.momo.member.domain.QMember.member;
import static com.woowacourse.momo.storage.domain.QGroupImage.groupImage;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

import com.woowacourse.momo.group.domain.search.GroupSearchRepositoryCustom;
import com.woowacourse.momo.group.domain.search.SearchCondition;
import com.woowacourse.momo.group.domain.search.dto.GroupIdRepositoryResponse;
import com.woowacourse.momo.group.domain.search.dto.GroupSummaryRepositoryResponse;

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
        List<Long> groupIds = queryFactory
                .select(group.id)
                .from(group)
                .where(conditionFilter.filterByCondition(condition))
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<GroupSummaryRepositoryResponse> groups = queryFactory
                .select(makeProjections())
                .from(group)
                .innerJoin(group.participants.host, member)
                .leftJoin(group.participants.participants, participant)
                .leftJoin(groupImage).on(group.id.eq(groupImage.groupId))
                .where(group.id.in(groupIds))
                .groupBy(group.id)
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(group.countDistinct())
                .from(group)
                .leftJoin(group.participants.participants, participant)
                .where(conditionFilter.filterByCondition(condition));

        return PageableExecutionUtils.getPage(groups, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GroupSummaryRepositoryResponse> findHostedGroups(SearchCondition condition, Long memberId,
                                                                 Pageable pageable) {
        return findGroups(condition, pageable, () -> isHost(memberId));
    }

    @Override
    public Page<GroupSummaryRepositoryResponse> findParticipatedGroups(SearchCondition condition, Long memberId,
                                                                       Pageable pageable) {
        return findGroups(condition, pageable, () -> isParticipated(memberId));
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
                .leftJoin(groupImage).on(group.id.eq(groupImage.groupId))
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
        List<Long> groupIds = queryFactory
                .select(makeGroupIdRepositoryResponse())
                .distinct()
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
                .fetch()
                .stream()
                .map(GroupIdRepositoryResponse::getGroupId)
                .collect(Collectors.toUnmodifiableList());

        List<GroupSummaryRepositoryResponse> groups = queryFactory
                .select(makeProjections())
                .from(group)
                .innerJoin(group.participants.host, member)
                .leftJoin(group.participants.participants, participant)
                .leftJoin(groupImage).on(group.id.eq(groupImage.groupId))
                .where(group.id.in(groupIds))
                .groupBy(group.id)
                .orderBy(orderByDeadlineAsc(condition.orderByDeadline()).toArray(OrderSpecifier[]::new))
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
                group.calendar.deadline.value,
                groupImage.imageName
        );
    }

    private BooleanExpression isHost(Long memberId) {
        return group.participants.host.id.eq(memberId);
    }

    private BooleanExpression isParticipated(Long memberId) {
        return isHost(memberId).or(participant.member.id.eq(memberId));
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

    private ConstructorExpression<GroupIdRepositoryResponse> makeGroupIdRepositoryResponse() {
        return Projections.constructor(GroupIdRepositoryResponse.class, group.id, group.calendar);
    }
}
