package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,String> {
    List<Player> findAll();
    Optional<Player> findPlayerById(String id);
    Optional<Player> findPlayerByName(String name);

}
