package com.hungnln.vleague.entity;

import com.hungnln.vleague.constant.activity.RefereeMatchRole;
import com.hungnln.vleague.entity.key.RefereeMatchParticipationKey;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "refereematchparticipation")
@Builder
@Data
@ToString
public class RefereeMatchParticipation {
    @EmbeddedId
    private RefereeMatchParticipationKey id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("refereeId")
    @JoinColumn(name = "refereeid")
    private Referee referee;
    @MapsId("matchId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchid")
    private Match match;
    @Column(name = "role")
    private RefereeMatchRole role;
}
