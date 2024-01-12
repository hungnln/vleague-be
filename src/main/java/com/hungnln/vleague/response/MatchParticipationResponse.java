package com.hungnln.vleague.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hungnln.vleague.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MatchParticipationResponse {
    private UUID matchId;
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Match match;
    private Collection<PlayerMatchParticipation> playerMatchParticipations;
}
