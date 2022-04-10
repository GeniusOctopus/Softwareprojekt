package com.softwareproject.backend.repository;

import com.softwareproject.backend.response.RankingResponse;
import com.softwareproject.backend.response.WinnerOnLeftAndRightSideResponse;
import com.softwareproject.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(
            value = "SELECT new com.softwareproject.backend.response.RankingResponse(i, i.datetime, COUNT(i.url)) " +
                    "FROM Vote v JOIN v.fk_ImageId_Winner i " +
                    "GROUP BY i.url"
    )
    List<RankingResponse> getWins();

    @Query(
            value = "SELECT new com.softwareproject.backend.response.RankingResponse(i, i.datetime, COUNT(i.url)) " +
                    "FROM Vote v JOIN v.fk_ImageId_Loser i " +
                    "GROUP BY i.url"
    )
    List<RankingResponse> getLoses();

    @Query(
            value = "SELECT new com.softwareproject.backend.response.WinnerOnLeftAndRightSideResponse(v.winnerOnLeftSide, COUNT(v.winnerOnLeftSide)) " +
                    "FROM Vote v GROUP BY v.winnerOnLeftSide"
    )
    List<WinnerOnLeftAndRightSideResponse> getCountOfWInnerOnLeftAndRightSide();

    @Query(
            value = "SELECT v.datetime FROM Vote v"
    )
    List<Long> findDatetime();

    @Query(
            value = "SELECT v.duration FROM Vote v"
    )
    List<Integer> findDuration();
}
