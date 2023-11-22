package com.hungnln.vleague.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StadiumResponse {
    private UUID id;
    private String name;
    private String address;
    private String imageURL;
}

