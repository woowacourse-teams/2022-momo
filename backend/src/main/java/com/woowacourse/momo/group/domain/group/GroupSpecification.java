package com.woowacourse.momo.group.domain.group;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.participant.domain.Participant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupSpecification {

    public static Specification<Group> initialize() {
        return (root, query, criteriaBuilder) -> null;
    }

    public static Specification<Group> filterByParticipated(Long memberId) {
        return (root, query, criteriaBuilder) -> {
            Join<Participant, Group> groupParticipant = root.join("participants");
            return criteriaBuilder.equal(groupParticipant.get("member"), memberId);
        };
    }

    public static Specification<Group> filterByHosted(Long memberId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("host"), memberId);
    }

    public static Specification<Group> filterByCategory(Long categoryId) {
        if (categoryId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        Category category = Category.from(categoryId);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Group> containKeyword(String keyword) {
        if (keyword == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            Predicate nameContainKeyword = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate descriptionContainKeyword = criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
            return criteriaBuilder.or(nameContainKeyword, descriptionContainKeyword);
        };
    }

    public static Specification<Group> excludeFinishedRecruitment(Boolean excludeFinished) {
        if (excludeFinished == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
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

    public static Specification<Group> orderByDeadline(Boolean orderByDeadline) {
        if (orderByDeadline == null) {
            return orderByIdDesc();
        }
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("deadline")), criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.greaterThan(root.get("deadline"), criteriaBuilder.currentTimestamp());
        };
    }

    private static Specification<Group> orderByIdDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return null;
        };
    }
}
