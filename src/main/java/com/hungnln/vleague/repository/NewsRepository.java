package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.Club;
import com.hungnln.vleague.entity.News;
import com.hungnln.vleague.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID>, JpaSpecificationExecutor<News> {
    List<News> findAll();
    Optional<News> findNewsById(UUID id);
    @Query(nativeQuery = true,value = "SELECT distinct * FROM news p WHERE " +
           " CASE"+
           " WHEN :newsIds IS NULL THEN 1"+
           " ELSE p.id IN ( :newsIds )"+
           " END = 1")
    Page<News> findNews(@Param("newsIds") List<UUID> newsIds, Pageable pageable);
//    @Query(value = "SELECT distinct n FROM news n JOIN n.players p JOIN n.clubs c WHERE" +
//            " (CASE"+
//            " WHEN :playerIds IS NULL THEN 1"+
//            " ELSE p.id IN (:playerIds)"+
//            " END = 1)"+
//            " AND"+
//            " (CASE"+
//            " WHEN :clubIds IS NULL THEN 1"+
//            " ELSE c.id IN ( :clubIds )"+
//            " END = 1)")
//    @Query(nativeQuery = true,value = "SELECT distinct n.*,p.*,c.* FROM news n " +
//            " JOIN newsplayer  ON n.id = newsplayer.newsid" +
//            " JOIN players p ON p.id = newsplayer.playerid" +
//            " JOIN newsclub ON n.id = newsclub.newsid" +
//            " JOIN clubs c ON c.id = newsclub.clubid" +
//            " WHERE" +
//            " (CASE"+
//            " WHEN :playerIds IS NULL THEN 1"+
//            " ELSE p.id IN (:playerIds)"+
//            " END = 1)"+
//            " AND"+
//            " (CASE"+
//            " WHEN :clubIds IS NULL THEN 1"+
//            " ELSE c.id IN ( :clubIds )"+
//            " END = 1)")
//@Query(value = "SELECT n FROM News n JOIN n.players p JOIN n.clubs c WHERE " +
//        " (:playerIds IS NULL OR p.id IN :playerIds) AND " +
//        " (:clubIds IS NULL OR c.id IN :clubIds)")
@Query(nativeQuery = true,value = "SELECT * FROM news n")
    Page<News> findNewsByPlayersIdsAndClubsIds(@Param("playerIds") List<UUID> playerIds,@Param("clubIds")  List<UUID> clubIds,Pageable pageable);

}

