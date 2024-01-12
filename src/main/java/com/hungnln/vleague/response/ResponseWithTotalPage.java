package com.hungnln.vleague.response;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseWithTotalPage<T> {
    private PaginationResponse pagination;
    private List<T> data;
}
