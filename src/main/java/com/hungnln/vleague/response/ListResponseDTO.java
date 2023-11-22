package com.hungnln.vleague.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ListResponseDTO<T> {
    private String message;
    private List<T> data;
    private String status;
}
