package com.hungnln.vleague.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


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
    private String id;
    private String name;
    private String imageURL;
    @Column(name = "dateofbirth")
    private Date dateOfBirth;
    @Column(name = "heightcm")
    private float heightCm;
    @Column(name = "weightkg")
    private float weightKg;
}
