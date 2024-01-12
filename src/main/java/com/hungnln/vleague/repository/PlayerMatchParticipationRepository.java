package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.PlayerMatchParticipation;
import com.hungnln.vleague.entity.key.PlayerMatchParticipationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PlayerMatchParticipationRepository extends JpaRepository<PlayerMatchParticipation, PlayerMatchParticipationKey> {
}
