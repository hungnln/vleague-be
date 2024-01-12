package com.hungnln.vleague.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ClubNewsKey implements Serializable {

    @Column(name = "clubid")
    private UUID clubId;

    @Column(name = "newsid")
    private UUID newsId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
