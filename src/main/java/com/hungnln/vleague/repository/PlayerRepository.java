package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID>, JpaSpecificationExecutor<Player> {

@Query(nativeQuery = true,value = "SELECT distinct * FROM players p WHERE " +
        " CASE"+
        " WHEN :playerIds IS NULL THEN 1"+
        " ELSE p.id IN ( :playerIds )"+
        " END = 1")
    List<Player> findAllByPlayerIds(@Param("playerIds") List<UUID> playerIds);
    Optional<Player> findPlayerById(UUID id);
    Optional<Player> findPlayerByName(String name);
}
