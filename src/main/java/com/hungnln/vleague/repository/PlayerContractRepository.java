package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.PlayerContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerContractRepository extends JpaRepository<PlayerContract, UUID>, JpaSpecificationExecutor<PlayerContract> {
    Optional<PlayerContract> findPlayerContractById(UUID id);
//    Optional<PlayerContract> findPlayerContractByPlayer(String name);
    @Query(nativeQuery = true,value = "SELECT * FROM playercontracts p WHERE p.playerid = :playerId " +
            "AND (" +
            "    (p.start BETWEEN :start AND :end" +
            "    OR p.end BETWEEN :start AND :end)" +
            "    OR (:start BETWEEN p.start AND p.end)" +
            ")")
    List<PlayerContract> findAllByStartGreaterThanEqualAndEndLessThanEqualAndPlayerId(@Param("start") String start,@Param("end") String end,@Param("playerId") UUID playerId);
//    @Query(nativeQuery = true,value = "SELECT COUNT * FROM playercontracts p WHERE p.number = :number "+
//            "AND (" +
//            "    p.start BETWEEN :start AND :end" +
//            "    OR p.end BETWEEN :start AND :end" +
//            "    OR :start BETWEEN p.start AND p.end" +
//            ")")
    @Query(nativeQuery = true,value = "SELECT COUNT(*) FROM playercontracts p WHERE p.clubId = :clubId AND p.number = :number " +
            "AND (" +
            "    (p.start BETWEEN :start AND :end" +
            "    OR p.end BETWEEN :start AND :end)" +
            "    OR (:start BETWEEN p.start AND p.end)" +
            ")")
    int countAllByClubAndByStartGreaterThanEqualAndEndLessThanEqual(@Param("start") String start, @Param("end") String end, @Param("number") int number,@Param("clubId") UUID clubId);


}

