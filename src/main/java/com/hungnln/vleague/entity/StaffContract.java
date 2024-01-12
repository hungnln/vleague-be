package com.hungnln.vleague.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "staffcontracts")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor()
@NoArgsConstructor()
public class StaffContract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "staffid")
    private Staff staff;
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "clubid")
    private Club club;
    private float salary;
    @Temporal(TemporalType.DATE)
    private Date start;
    @Temporal(TemporalType.DATE)
    private Date end;
    private String description;

    @AssertTrue
    private boolean isValidDateRange() {
        return start.before(end);
    }
}
