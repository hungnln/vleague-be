package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.RefereeMatchParticipation;
import com.hungnln.vleague.entity.key.RefereeMatchParticipationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeMatchParticipationRepository extends JpaRepository<RefereeMatchParticipation, RefereeMatchParticipationKey> {
}
