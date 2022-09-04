package com.woowacourse.momo.group.service.specification;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.woowacourse.momo.group.domain.Group;

public enum GroupAttribute {

    ID("id"),
    HOST("participants", "host"),
    NAME("name", "value"),
    CATEGORY("category"),
    CAPACITY("participants", "capacity"),
    DEADLINE("calendar", "deadline"),
    DESCRIPTION("description"),
    CLOSED_EARLY("closedEarly"),
    ;

    private final String startAttribute;
    private final List<String> attributes;

    GroupAttribute(String startAttribute, String... attributes) {
        this.startAttribute = startAttribute;
        this.attributes = List.of(attributes);
    }

    public <T> Expression<T> from(Root<Group> root) {
        Path<T> path = root.get(startAttribute);
        for (String attribute : attributes) {
            path = path.get(attribute);
        }
        return path;
    }
}
