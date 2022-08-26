package com.woowacourse.momo.group.service.specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

@Component
public class GroupSpecification {

    public Specification<Group> initialize() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public Specification<Group> filterByParticipated(Member member) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(ParticipantAttribute.MEMBER.from(root), member);
    }

    public Specification<Group> filterByHosted(Member member) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(GroupAttribute.HOST.from(root), member);
    }

    public Specification<Group> filterByCategory(Long categoryId) {
        if (categoryId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        Category category = Category.from(categoryId);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(GroupAttribute.CATEGORY.from(root), category);
    }

    public Specification<Group> containKeyword(String keyword) {
        if (keyword == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            Predicate nameContainKeyword = criteriaBuilder.like(GroupAttribute.NAME.from(root), "%" + keyword + "%");
            Predicate descriptionContainKeyword = criteriaBuilder.like(
                    GroupAttribute.DESCRIPTION.from(root), "%" + keyword + "%");
            return criteriaBuilder.or(nameContainKeyword, descriptionContainKeyword);
        };
    }

    public Specification<Group> excludeFinished(Boolean excludeFinished) {
        if (excludeFinished == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            query.groupBy(GroupAttribute.ID.from(root));

            Expression<Long> count = criteriaBuilder.count(ParticipantAttribute.ID.from(root));
            Predicate isOverCapacity = criteriaBuilder.lessThan(count, GroupAttribute.CAPACITY.from(root));
            query.having(isOverCapacity);

            Predicate isEarlyClosed = criteriaBuilder.isFalse(GroupAttribute.IS_EARLY_CLOSED.from(root));
            Predicate isOverDeadline = criteriaBuilder.greaterThan(GroupAttribute.DEADLINE.from(root),
                    criteriaBuilder.currentTimestamp());
            return criteriaBuilder.and(isEarlyClosed, isOverDeadline);
        };
    }

    public Specification<Group> orderByDeadline(Boolean orderByDeadline) {
        if (orderByDeadline == null) {
            return orderByIdDesc();
        }
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(GroupAttribute.DEADLINE.from(root)), criteriaBuilder.desc(GroupAttribute.ID.from(root)));
            return criteriaBuilder.greaterThan(GroupAttribute.DEADLINE.from(root), criteriaBuilder.currentTimestamp());
        };
    }

    private Specification<Group> orderByIdDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(GroupAttribute.ID.from(root)));
            return criteriaBuilder.conjunction();
        };
    }
}
