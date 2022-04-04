package com.softwareproject.backend.repository;

import com.softwareproject.backend.api.RankingResponse;
import com.softwareproject.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(
            value = "SELECT new com.softwareproject.backend.api.RankingResponse(i, i.datetime, COUNT(i.url)) " +
                    "FROM Vote v JOIN v.fk_ImageId_Winner i " +
                    "GROUP BY i.url")
    List<RankingResponse> getWins();

    @Query(
            value = "SELECT new com.softwareproject.backend.api.RankingResponse(i, i.datetime, COUNT(i.url)) " +
                    "FROM Vote v JOIN v.fk_ImageId_Loser i " +
                    "GROUP BY i.url")
    List<RankingResponse> getLoses();
}
