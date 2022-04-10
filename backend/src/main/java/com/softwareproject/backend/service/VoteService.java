package com.softwareproject.backend.service;

import com.softwareproject.backend.response.*;
import com.softwareproject.backend.model.BasicStatisticData;
import com.softwareproject.backend.model.Ranking;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.model.WinnerOnLeftSide;
import com.softwareproject.backend.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
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

    public WinnerOnLeftSide getCountOfWinnerOnLeftAndRightSide() {

        List<WinnerOnLeftAndRightSideResponse> winnerOnLeftAndRightSideResponseList = voteRepository.getCountOfWInnerOnLeftAndRightSide();

        WinnerOnLeftAndRightSideResponse winnerOnLeftSideCount = winnerOnLeftAndRightSideResponseList.stream()
                .filter(WinnerOnLeftAndRightSideResponse::isWinnerOnLeftSide)
                .findFirst()
                .orElse(new WinnerOnLeftAndRightSideResponse(true, 0));

        WinnerOnLeftAndRightSideResponse winnerOnRightSideCount = winnerOnLeftAndRightSideResponseList.stream()
                .filter(winnerOnLeftAndRightSideResponse -> !winnerOnLeftAndRightSideResponse.isWinnerOnLeftSide())
                .findFirst()
                .orElse(new WinnerOnLeftAndRightSideResponse(false, 0));

        return new WinnerOnLeftSide(
                (int) winnerOnLeftSideCount.getCount(),
                (int) winnerOnRightSideCount.getCount()
        );
    }

    public BasicStatisticData getBasicStatisticData() {

        List<Long> datetimeList = voteRepository.findDatetime();
        BasicStatisticData basicStatisticData = new BasicStatisticData();

        basicStatisticData.setVotesTotal(datetimeList.size());
        basicStatisticData.setVotesLastTwentyFourHours((int) datetimeList.stream()
                .filter(datetime -> {
                    LocalDateTime localDateTimeInserted = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), TimeZone.getDefault().toZoneId());
                    return localDateTimeInserted.isAfter(LocalDateTime.now().minusDays(1));
                }).count());
        basicStatisticData.setVotesLastSevenDays((int) datetimeList.stream()
                .filter(datetime -> {
                    LocalDateTime localDateTimeInserted = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), TimeZone.getDefault().toZoneId());
                    return localDateTimeInserted.isAfter(LocalDateTime.now().minusWeeks(1));
                }).count());

        return basicStatisticData;
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

        for (int i = 0; i < rankingListOrdered.size(); i++) {
            double lastWinsPerVote = i == 0
                    ? -1
                    : rankingListOrdered.get(i - 1).getWinsPerVote();
            if (lastWinsPerVote == rankingListOrdered.get(i).getWinsPerVote()) {
                rankingListOrdered.get(i).setRank(rankingListOrdered.get(i - 1).getRank());
            } else {
                rankingListOrdered.get(i).setRank(i + 1);
            }
        }

        return rankingListOrdered;
    }

    private double getWinsPerVote(int numberOfWins, int numberOfLoses) {

        return (double) numberOfWins / (numberOfWins + numberOfLoses);
    }
}
