package com.woowacourse.momo.group.repository;

import static com.woowacourse.momo.group.domain.QGroup.group;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.QGroup;
import com.woowacourse.momo.group.service.dto.request.GroupFindRequest;

@Repository
public class GroupRepositoryCustomImpl implements GroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GroupRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Group> findGroups(GroupFindRequest request, Pageable pageable) {
        QGroup group = QGroup.group;

        List<Group> groups = queryFactory
                .select(group)
                .from(group)
                .where(excludeFinished(request.excludeFinished(), group),
                        filterByCategory(request.getCategory()),
                        containKeyword(request.getKeyword()),
                        afterNow(request.orderByDeadline()))
                .orderBy(orderByDeadlineAsc(request.orderByDeadline()).toArray(OrderSpecifier[]::new))
                .fetch();

        return PageableExecutionUtils.getPage(groups, pageable, groups::size);
    }

    private BooleanExpression excludeFinished(boolean excludeFinished, QGroup group) {
        if (!excludeFinished) {
            return null;
        }
        return afterNow(true)
                .and(notClosedEarly())
                .and(isNotFull(group));
    }

    private BooleanExpression isNotFull(QGroup group) {
        return group.participants.capacity.value.gt(
                group.participants.participants.size().add(Expressions.constant(1)));
    }

    private BooleanExpression notClosedEarly() {
        return group.closedEarly.isFalse();
    }

    private BooleanExpression filterByCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        Category category = Category.from(categoryId);
        return group.category.eq(category);
    }

    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }

        BooleanExpression nameContains = group.name.value.contains(keyword);
        BooleanExpression descriptionContains = group.description.contains(keyword);
        return nameContains.or(descriptionContains);
    }

    private BooleanExpression afterNow(boolean orderByDeadline) {
        if (!orderByDeadline) {
            return null;
        }
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

//    @Override
//    public Page<Group> findParticipatedGroups(GroupFindRequest request, Member member, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Page<Group> findHostedGroups(GroupFindRequest request, Member member, Pageable pageable) {
//        return null;
//    }
}
