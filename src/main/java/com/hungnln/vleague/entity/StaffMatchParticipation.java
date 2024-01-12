package com.hungnln.vleague.entity;

import com.hungnln.vleague.constant.activity.StaffMatchRole;
import com.hungnln.vleague.entity.key.StaffMatchParticipationKey;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "staffmatchparticipation")
@Builder
@Data
@ToString
public class StaffMatchParticipation {
    @EmbeddedId
    private StaffMatchParticipationKey id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("staffContractId")
    @JoinColumn(name = "staffcontractid")
    private StaffContract staffContract;
    @MapsId("matchId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchid")
    private Match match;
    @Column(name = "role")
    private StaffMatchRole role;
}
