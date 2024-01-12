package com.hungnln.vleague.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class PlayerNewsKey implements Serializable {

    @Column(name = "playerid")
    private UUID playerId;

    @Column(name = "newsid")
    private UUID newsId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
