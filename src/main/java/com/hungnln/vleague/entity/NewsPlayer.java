package com.hungnln.vleague.entity;

import com.hungnln.vleague.entity.key.PlayerNewsKey;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "newsplayer")
@Builder
public class NewsPlayer implements Serializable {
    @EmbeddedId
    PlayerNewsKey id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    @JoinColumn(name = "playerid")
    private Player player;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("newsId")
    @JoinColumn(name = "newsid")
    private News news;

}
