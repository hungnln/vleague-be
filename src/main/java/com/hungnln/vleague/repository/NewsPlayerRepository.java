package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.NewsPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface NewsPlayerRepository extends JpaRepository<NewsPlayer, UUID> {
    @Query(nativeQuery = true, value = "SELECT distinct p.playerid FROM newsplayer p WHERE " +
            " CASE"+
            " WHEN :newsId IS NULL THEN 1"+
            " ELSE p.newsid = :newsId"+
            " END = 1")
    List<UUID> getListPlayerByNewsId(@Param("newsId") UUID newsId);
    @Query(nativeQuery = true, value = "SELECT distinct newsid " +
            "FROM newsplayer " +
            "WHERE playerid in (:playerIds)")
    List<UUID> getListNewsByPlayerId(@Param("playerIds") List<UUID> playerIds);
}
