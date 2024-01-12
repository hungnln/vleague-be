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
public class RefereeMatchParticipationKey implements Serializable {
    @Column(name = "refereeid")
    private UUID refereeId;
    @Column(name = "matchid")
    private UUID matchId;
}
