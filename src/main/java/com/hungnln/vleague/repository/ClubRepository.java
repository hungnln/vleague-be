package com.hungnln.vleague.repository;


import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ClubRepository extends JpaRepository<Club, UUID>, JpaSpecificationExecutor<Club> {
    Page<Club> findAll(Pageable pageable);
    Optional<Club> findClubById(UUID id);
    Optional<Club> findClubByName(String name);
//    List<Club> findClubsByStadiumId(String name);
@Query(nativeQuery = true,value = "SELECT distinct * FROM clubs p WHERE " +
        " CASE"+
        " WHEN :clubIds IS NULL THEN 1"+
        " ELSE p.id IN ( :clubIds )"+
        " END = 1")
List<Club> findAllByClubIds(@Param("clubIds") List<UUID> clubIds);
}
