package com.hungnln.vleague.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseDTO<T> {
    private String status;
    private String message;
    private T data;
}
