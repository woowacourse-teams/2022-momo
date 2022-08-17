package com.woowacourse.momo.group.service.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupFindRequest {

    @NotNull
    private Integer page = 0;
    @NotNull
    private Long category;
    @NotNull
    private String keyword;
    @NotNull
    private Boolean excludeFinished;
    @NotNull
    private Boolean orderByDeadline;
}
