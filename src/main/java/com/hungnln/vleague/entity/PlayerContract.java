package com.hungnln.vleague.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "playercontracts")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor()
public class PlayerContract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerid")
    private Player player;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "clubid")
    private Club club;
    private int number;
    private float salary;
    @Temporal(TemporalType.DATE)
    private Date start;
    @Temporal(TemporalType.DATE)
    private Date end;
    private String description;
    @OneToMany(mappedBy = "playerContract")
    @JsonIgnore
    private Collection<PlayerMatchParticipation> playerMatchParticipations;
    @AssertTrue
    private boolean isValidDateRange() {
        return start.before(end);
    }
}
