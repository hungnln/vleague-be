package com.hungnln.vleague.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class MatchActivityPlayerMatchParticipationKey implements Serializable {
    @Column(name = "activitiesid")
    private UUID activitiesId;
    @Column(name = "playerinvolvedplayercontractid")
    private UUID playerInvolvedPlayerContractId;
    @Column(name = "playerinvolvedmatchid")
    private UUID playerInvolvedMatchId;
}
