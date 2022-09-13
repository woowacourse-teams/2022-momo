package com.woowacourse.momo.group.service.specification;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.woowacourse.momo.member.domain.Member;

public enum MemberAttribute {

    ID("id"),
    ;

    private final String startAttribute;
    private final List<String> attributes;

    MemberAttribute(String startAttribute, String... attributes) {
        this.startAttribute = startAttribute;
        this.attributes = List.of(attributes);
    }

    public <T> Expression<T> from(Root<Member> root) {
        Path<T> path = root.get(startAttribute);
        for (String attribute : attributes) {
            path = path.get(attribute);
        }
        return path;
    }
}
