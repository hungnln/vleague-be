package com.hungnln.vleague.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Table(name="players")
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String imageURL;
    @Column(name = "dateofbirth")
    private Date dateOfBirth;
    @Column(name = "heightcm")
    private int heightCm;
    @Column(name = "weightkg")
    private int weightKg;
    @JsonIgnore
    @ManyToMany(mappedBy = "players")
    private Collection<News> news;
}
