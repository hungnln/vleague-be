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
public class StaffMatchParticipationKey implements Serializable {
    @Column(name = "staffcontractid")
    private UUID staffContractId;
    @Column(name = "matchid")
    private UUID matchId;
}
