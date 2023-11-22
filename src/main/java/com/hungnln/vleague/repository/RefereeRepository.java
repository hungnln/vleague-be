package com.hungnln.vleague.repository;


import com.hungnln.vleague.entity.Referee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefereeRepository extends JpaRepository<Referee, UUID> {
    List<Referee> findAll();
    Optional<Referee> findRefereeById(UUID id);
    Optional<Referee> findRefereeByName(String name);
}
