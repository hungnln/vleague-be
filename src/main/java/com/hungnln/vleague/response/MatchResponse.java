package com.hungnln.vleague.response;

import com.hungnln.vleague.entity.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponse {
    private UUID id;
    private Date startDate;
    private Date endDate;
    private UUID homeClubId;
    private Club homeClub;
    private UUID awayClubId;
    private Club awayClub;
    private UUID stadiumId;
    private Stadium stadium;
    private UUID roundId;
    private Round round;
    private int homeGoals;
    private int awayGoals;
    private Collection<MatchActivity> activities;
}
