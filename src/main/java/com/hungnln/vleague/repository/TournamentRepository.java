package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
    List<Tournament> findAll();
    Optional<Tournament> findTournamentById(UUID id);
    Optional<Tournament> findTournamentByName(String name);
}
