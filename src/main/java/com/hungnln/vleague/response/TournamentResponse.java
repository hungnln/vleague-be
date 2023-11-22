package com.hungnln.vleague.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TournamentResponse {
    private UUID id;
    private String name;
    private Date start;
    private Date end;
}
