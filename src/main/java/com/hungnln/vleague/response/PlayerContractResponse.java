package com.hungnln.vleague.response;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Player;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlayerContractResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID playerId;
    private Player player;
    private UUID clubId;
    private Club club;
    private int number;
    private double salary;
    private Date start;
    private Date end;
    private String description;
}
