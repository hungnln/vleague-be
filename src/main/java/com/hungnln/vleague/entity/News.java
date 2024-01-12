package com.hungnln.vleague.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "news")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor()
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "createdat")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private String title;
    @Column(name = "thumbnailimageurl")
    private String thumbnailImageURL;
    private String content;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
//    @JoinColumn(name = "playercontractid")
//    private PlayerContract playerContract;
    @ManyToMany
    @JoinTable(
            name = "newsclub",
            joinColumns = @JoinColumn(name = "newsid"),
            inverseJoinColumns = @JoinColumn(name = "clubid"))
    private Collection<Club> clubs;
    @ManyToMany
    @JoinTable(
            name = "newsplayer",
            joinColumns = @JoinColumn(name = "newsid"),
            inverseJoinColumns = @JoinColumn(name = "playerid"))
    private Collection<Player> players;
//    @OneToMany(mappedBy = "news")
//    private Collection<NewsClub> newsClubs;
//    @OneToMany(mappedBy = "news")
//    private Collection<NewsPlayer> newsPlayers;
}
