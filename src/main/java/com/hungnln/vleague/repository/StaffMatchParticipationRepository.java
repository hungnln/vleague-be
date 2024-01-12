package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.StaffMatchParticipation;
import com.hungnln.vleague.entity.key.StaffMatchParticipationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffMatchParticipationRepository extends JpaRepository<StaffMatchParticipation, StaffMatchParticipationKey> {
}
