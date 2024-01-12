package com.hungnln.vleague.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungnln.vleague.constant.activity.PlayerMatchRole;
import com.hungnln.vleague.entity.key.PlayerMatchParticipationKey;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "playermatchparticipation")
@Builder
@Data
@ToString
public class PlayerMatchParticipation {
    @EmbeddedId
    @JsonIgnore
    private PlayerMatchParticipationKey id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerContractId")
    @JoinColumn(name = "playercontractid")
    private PlayerContract playerContract;
    @MapsId("matchId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchid")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Match match;
    @Column(name = "inlineups")
    private boolean inLineups;
    @Column(name = "role")
    private PlayerMatchRole role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerid")
    private Player player;
}
