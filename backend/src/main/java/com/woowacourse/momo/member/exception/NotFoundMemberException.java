package com.woowacourse.momo.member.exception;

public class NotFoundMemberException extends MemberException {

    public NotFoundMemberException() {
        super("존재하지 않는 회원입니다.");
    }
}
