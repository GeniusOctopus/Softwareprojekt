package com.softwareproject.backend.service;

import com.softwareproject.backend.api.Ranking;
import com.softwareproject.backend.api.RankingResponse;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class VoteService {

    final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAllIVotes() {

        return voteRepository.findAll();
    }

    public Vote addVote(@Valid Vote vote) {

        return voteRepository.save(vote);
    }

    public List<Ranking> getRanking() {

        List<RankingResponse> winsResponseList = voteRepository.getWins();
        List<RankingResponse> losesResponseList = voteRepository.getLoses();
        List<Ranking> rankingList = new ArrayList<>();

        for (RankingResponse rankingResponse : winsResponseList) {
            rankingList.add(new Ranking(
                    rankingResponse.getUrl(),
                    System.currentTimeMillis(),
                    Math.toIntExact(rankingResponse.getValue()),
                    0,
                    rankingResponse.getDatetime(),
                    1
            ));
        }

        for (RankingResponse rankingResponse : losesResponseList) {
            boolean isImageAlreadyInList = rankingList.stream().anyMatch(rr -> rr.getUrl().equals(rankingResponse.getUrl()));
            if (isImageAlreadyInList) {
                rankingList.stream()
                        .filter(rr -> rankingResponse.getUrl().equals(rr.getUrl()))
                        .findFirst()
                        .ifPresentOrElse(ranking -> {
                            ranking.setLoses(Math.toIntExact(rankingResponse.getValue()));
                            ranking.setWinsPerVote(getWinsPerVote(ranking.getWins(), ranking.getLoses()));
                        }, null);

            } else {
                rankingList.add(new Ranking(
                        rankingResponse.getUrl(),
                        System.currentTimeMillis(),
                        0,
                        Math.toIntExact(rankingResponse.getValue()),
                        rankingResponse.getDatetime(),
                        0
                ));
            }
        }

        return rankingList;
    }

    private double getWinsPerVote(int numberOfWins, int numberOfLoses) {

        return (double) numberOfWins / (numberOfWins + numberOfLoses);
    }
}
