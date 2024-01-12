package com.hungnln.vleague.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungnln.vleague.response.MatchParticipationResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "matches")
@Builder
@Data
@ToString
public class Match implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "enddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "homeclubid")
    private Club homeClub;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "awayclubid")
    private Club awayClub;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "stadiumid")
    private Stadium stadium;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "roundid")
    private Round round;
//    @OneToMany(mappedBy = "match",cascade = CascadeType.ALL)
//    @EqualsAndHashCode.Include
//    @ToString.Include
//    private Collection<MatchActivity> activities;
    @JsonIgnore
    @OneToMany(mappedBy = "match")
    private Collection<PlayerMatchParticipation> playerMatchParticipations;
}
