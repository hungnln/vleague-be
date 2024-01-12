package com.hungnln.vleague.entity;

import com.hungnln.vleague.entity.key.MatchActivityPlayerMatchParticipationKey;
import jakarta.persistence.*;

import java.util.UUID;
@IdClass(MatchActivityPlayerMatchParticipationKey.class)
public class MatchActivityPlayerMatchParticipation {
//    @EmbeddedId
//    MatchActivityPlayerMatchParticipationKey id;
//    @ManyToOne
//    @MapsId("activitiesId")
//    @JoinColumn(name = "activitiesId")
//    private MatchActivity matchActivity;
//    private PlayerMatchParticipation
    @Id
    @Column(name = "activitiesid")
    private UUID activitiesId;

    @Id
    @Column(name = "playerinvolvedplayercontractid")
    private UUID playerInvolvedPlayerContractId;

    @Id
    @Column(name = "playerinvolvedmatchid")
    private UUID playerInvolvedMatchId;
}
