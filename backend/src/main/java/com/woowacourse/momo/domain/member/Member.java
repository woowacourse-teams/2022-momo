package com.woowacourse.momo.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    public Member(String name) {
        this.name = name;
    }
    public Member(Long id, String name) {
        this.id = id; this.name = name;
    }
}
