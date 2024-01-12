package com.hungnln.vleague.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class PaginationResponse {
    private int totalPage;
    private int totalCount;
    private int pageSize;
    private int pageIndex;
}
