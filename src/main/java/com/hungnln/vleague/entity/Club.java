package com.hungnln.vleague.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
@Entity
@Table(name = "clubs")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor()
public class Club implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(name = "headquarter")
    private String headQuarter;
    private String imageURL;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "stadiumid")
    private Stadium stadium;
//    @OneToMany(mappedBy = "club")
//    private Collection<NewsClub> newsClubs;
    @JsonIgnore
    @ManyToMany(mappedBy = "clubs")
    private Collection<News> news;
}
