package com.hungnln.vleague.response;

import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Round;
import com.hungnln.vleague.entity.Stadium;
import com.hungnln.vleague.entity.Tournament;
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
}
