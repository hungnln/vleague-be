package com.hungnln.vleague.entity;

import com.hungnln.vleague.entity.key.ClubNewsKey;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "newsclub")
@Builder
public class NewsClub implements Serializable {
    @EmbeddedId
    ClubNewsKey id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clubId")
    @JoinColumn(name = "clubid")
    private Club club;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("newsId")
    @JoinColumn(name = "newsid")
    private News news;

}
