package com.softwareproject.backend.service;

import com.softwareproject.backend.api.Ranking;
import com.softwareproject.backend.api.RankingResponse;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                    rankingResponse.getImage(),
                    System.currentTimeMillis(),
                    Math.toIntExact(rankingResponse.getValue()),
                    0,
                    1
            ));
        }

        for (RankingResponse rankingResponse : losesResponseList) {
            boolean isImageAlreadyInList = rankingList.stream().anyMatch(rr -> rr.getImage().equals(rankingResponse.getImage()));
            if (isImageAlreadyInList) {
                rankingList.stream()
                        .filter(rr -> rankingResponse.getImage().equals(rr.getImage()))
                        .findFirst()
                        .ifPresentOrElse(ranking -> {
                            ranking.setLoses(Math.toIntExact(rankingResponse.getValue()));
                            ranking.setWinsPerVote(getWinsPerVote(ranking.getWins(), ranking.getLoses()));
                        }, null);

            } else {
                rankingList.add(new Ranking(
                        rankingResponse.getImage(),
                        System.currentTimeMillis(),
                        0,
                        Math.toIntExact(rankingResponse.getValue()),
                        0
                ));
            }
        }

        List<Ranking> rankingListOrdered = rankingList.stream()
                .sorted(Comparator.comparing(Ranking::getWinsPerVote).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < rankingListOrdered.size(); i++){
            double lastWinsPerVote = i == 0
                    ? -1
                    : rankingListOrdered.get(i - 1).getWinsPerVote();
            if (lastWinsPerVote == rankingListOrdered.get(i).getWinsPerVote()){
                rankingListOrdered.get(i).setRank(rankingListOrdered.get(i - 1).getRank());
            }else {
                rankingListOrdered.get(i).setRank(i +1);
            }
        }

        return rankingListOrdered;
    }

    private double getWinsPerVote(int numberOfWins, int numberOfLoses) {

        return (double) numberOfWins / (numberOfWins + numberOfLoses);
    }
}
