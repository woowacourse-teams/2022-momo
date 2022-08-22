package com.woowacourse.momo.group.domain.group;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.participant.domain.Participant;

@Component
public class GroupSpecification {

    public Specification<Group> initialize() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public Specification<Group> filterByParticipated(Member member) {
        return (root, query, criteriaBuilder) -> {
            Join<Participant, Group> groupParticipant = root.join("participants").join("participants");
            return criteriaBuilder.equal(groupParticipant.get("member"), member);
        };
    }

    public Specification<Group> filterByHosted(Member member) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("host"), member);
    }

    public Specification<Group> filterByCategory(Long categoryId) {
        if (categoryId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        Category category = Category.from(categoryId);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    public Specification<Group> containKeyword(String keyword) {
        if (keyword == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            Predicate nameContainKeyword = criteriaBuilder.like(root.get("name").get("value"), "%" + keyword + "%");
            Predicate descriptionContainKeyword = criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
            return criteriaBuilder.or(nameContainKeyword, descriptionContainKeyword);
        };
    }

    public Specification<Group> excludeFinished(Boolean excludeFinished) {
        if (excludeFinished == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) -> {
            root.join("participants").join("participants");
            query.groupBy(root.get("id"));

            Expression<Long> count = criteriaBuilder.count(root.get("id"));
            Predicate isOverCapacity = criteriaBuilder.lessThan(count, root.get("participants").get("capacity"));
            query.having(isOverCapacity);

            Predicate isEarlyClosed = criteriaBuilder.isFalse(root.get("isEarlyClosed"));
            Predicate isOverDeadline = criteriaBuilder.greaterThan(root.get("calendar").get("deadline"),
                    criteriaBuilder.currentTimestamp());
            return criteriaBuilder.and(isEarlyClosed, isOverDeadline);
        };
    }

    public Specification<Group> orderByDeadline(Boolean orderByDeadline) {
        if (orderByDeadline == null) {
            return orderByIdDesc();
        }
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("calendar").get("deadline")), criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.greaterThan(root.get("calendar").get("deadline"), criteriaBuilder.currentTimestamp());
        };
    }

    private Specification<Group> orderByIdDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.conjunction();
        };
    }
}
