package com.hungnln.vleague.response;

import com.hungnln.vleague.entity.Tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoundResponse {
    private UUID id;
    private UUID tournamentId;
    private Tournament tournament;
    private String name;
}
