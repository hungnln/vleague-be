package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.NewsClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsClubRepository extends JpaRepository<NewsClub, UUID> {
    @Query(nativeQuery = true, value = "SELECT distinct clubid " +
            "FROM newsclub " +
            "WHERE newsid = :newsId ")
    List<UUID> getListClubByNewsId(@Param("newsId") UUID newsId);
    @Query(nativeQuery = true, value = "SELECT distinct newsid " +
            "FROM newsclub " +
            "WHERE clubid in (:clubIds) ")
    List<UUID> getListNewsByClubId(@Param("clubIds") List<UUID> clubIds);
}
