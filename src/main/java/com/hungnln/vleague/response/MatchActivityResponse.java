package com.hungnln.vleague.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hungnln.vleague.constant.activity.ActivityType;
import com.hungnln.vleague.entity.Match;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class MatchActivityResponse {
    private UUID id;
    private Match match;
    private UUID matchId;
    private int minuteInMatch;
    private ActivityType type;
    private Date time;
}
