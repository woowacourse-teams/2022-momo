package com.woowacourse.momo.group.domain.group;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

public class GroupSpecification {

    public static Specification<Group> containKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Predicate nameContainKeyword = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate descriptionContainKeyword = criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
            return criteriaBuilder.or(nameContainKeyword, descriptionContainKeyword);
        };
    }

    public static Specification<Group> filterFinishedRecruitment() {
        return (root, query, criteriaBuilder) -> {
            root.join("participants");
            query.groupBy(root.get("id"));

            Expression<Long> count = criteriaBuilder.count(root.get("id"));
            Predicate isOverCapacity = criteriaBuilder.lessThan(count, root.get("capacity"));
            query.having(isOverCapacity);

            Predicate isEarlyClosed = criteriaBuilder.isFalse(root.get("isEarlyClosed"));
            Predicate isOverDeadline = criteriaBuilder.greaterThan(root.get("deadline"),
                    criteriaBuilder.currentTimestamp());
            return criteriaBuilder.and(isEarlyClosed, isOverDeadline);
        };
    }

    public static Specification<Group> orderByDeadline() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("deadline")), criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.greaterThan(root.get("deadline"), criteriaBuilder.currentTimestamp());
        };
    }

    public static Specification<Group> orderByIdDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return null;
        };
    }
}
