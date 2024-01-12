package com.hungnln.vleague.response;

import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Staff;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class StaffContractResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID staffId;
    private Staff staff;
    private UUID clubId;
    private Club club;
    private int number;
    private double salary;
    private Date start;
    private Date end;
    private String description;
}
