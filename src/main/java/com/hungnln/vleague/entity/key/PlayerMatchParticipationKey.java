package com.hungnln.vleague.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class PlayerMatchParticipationKey implements Serializable {
    @Column(name = "playercontractid")
    private UUID playerContractId;
    @Column(name = "matchid")
    private UUID matchId;
}
