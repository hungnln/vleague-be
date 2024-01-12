package com.hungnln.vleague.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponse {
    private UUID id;
    private String name;
    private String imageURL;
    private Date dateOfBirth;
    private float heightCm;
    private float weightKg;
}
