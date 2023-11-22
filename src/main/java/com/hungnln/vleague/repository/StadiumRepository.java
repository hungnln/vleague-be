package com.hungnln.vleague.repository;


import com.hungnln.vleague.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StadiumRepository extends JpaRepository<Stadium, UUID> {
    List<Stadium> findAll();
    Optional<Stadium> findStadiumById(UUID id);
    Optional<Stadium> findStadiumByName(String name);
}
