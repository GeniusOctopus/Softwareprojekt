package com.softwareproject.backend.service;

import com.softwareproject.backend.model.*;
import com.softwareproject.backend.response.RankingResponse;
import com.softwareproject.backend.response.WinnerOnLeftAndRightSideResponse;
import com.softwareproject.backend.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VoteServiceTest {

    private final VoteRepository voteRepository = mock(VoteRepository.class);
    private final VoteService voteService = new VoteService(voteRepository);

    private final List<Vote> voteListMock = new ArrayList<>();
    private Vote voteResponseAndRequest;
    private final Image imageWinner = new Image();
    private final Image imageLoser = new Image();
    private final Image imageOne = new Image(123, "abc", "http://abc.de", 100, 200, 2, "cbd");
    private final Image imageTwo = new Image(123, "def", "http://def.de", 100, 200, 2, "cbd");
    private final Image imageThree = new Image(123, "ghi", "http://ghi.de", 100, 200, 2, "cbd");

    @BeforeEach
    void setUp() {

        voteResponseAndRequest = new Vote(123, 123456789, imageWinner, imageLoser, 10, true);
        voteListMock.add(voteResponseAndRequest);
    }

    @Test
    void getAllIVotes() {

        when(voteRepository.findAll()).thenReturn(voteListMock);

        List<Vote> voteListResponse = voteService.getAllIVotes();

        assertEquals(1, voteListResponse.size());
        checkEntry(voteListMock.get(0), voteListResponse.get(0));

        verify(voteRepository, times(1)).findAll();
    }

    @Test
    void addVote() {

        when(voteRepository.save(voteResponseAndRequest)).thenReturn(voteResponseAndRequest);

        Vote voteResponse = voteService.addVote(voteResponseAndRequest);
        checkEntry(voteResponseAndRequest, voteResponse);

        verify(voteRepository, times(1)).save(voteResponseAndRequest);
    }

    @Test
    void getRanking() {

        List<RankingResponse> rankingResponseWinnerList = new ArrayList<>();
        rankingResponseWinnerList.add(new RankingResponse(imageOne, 1234, 3));
        rankingResponseWinnerList.add(new RankingResponse(imageTwo, 12345, 2));

        List<RankingResponse> rankingResponseLoserList = new ArrayList<>();
        rankingResponseLoserList.add(new RankingResponse(imageOne, 1234, 1));
        rankingResponseLoserList.add(new RankingResponse(imageTwo, 12345, 2));
        rankingResponseLoserList.add(new RankingResponse(imageThree, 12345, 5));

        when(voteRepository.getWins()).thenReturn(rankingResponseWinnerList);
        when(voteRepository.getLoses()).thenReturn(rankingResponseLoserList);

        List<Ranking> rankingList = voteService.getRanking();

        assertEquals(3, rankingList.size());
        checkRankingObject(new Ranking(imageOne, 3, 1, 0.75, 1), rankingList.get(0));
        checkRankingObject(new Ranking(imageTwo, 2, 2, 0.5, 2), rankingList.get(1));
        checkRankingObject(new Ranking(imageThree, 0, 5, 0.0, 3), rankingList.get(2));

        verify(voteRepository, times(1)).getWins();
        verify(voteRepository, times(1)).getLoses();
    }


    @Test
    void getCountOfWinnerOnLeftAndRightSide() {

        List<WinnerOnLeftAndRightSideResponse> winnerOnLeftAndRightSideResponseList = new ArrayList<>();
        winnerOnLeftAndRightSideResponseList.add(new WinnerOnLeftAndRightSideResponse(true, 5));
        winnerOnLeftAndRightSideResponseList.add(new WinnerOnLeftAndRightSideResponse(false, 8));


        when(voteRepository.getCountOfWInnerOnLeftAndRightSide()).thenReturn(winnerOnLeftAndRightSideResponseList);

        WinnerOnLeftSide winnerOnLeftSide = voteService.getCountOfWinnerOnLeftAndRightSide();

        checkEntry(new WinnerOnLeftSide(5, 8), winnerOnLeftSide);

        verify(voteRepository, times(1)).getCountOfWInnerOnLeftAndRightSide();
    }

    @Test
    void getCountOfWinnerOnLeftAndRightSideWithNoVotes() {

        when(voteRepository.getCountOfWInnerOnLeftAndRightSide()).thenReturn(new ArrayList<>());

        WinnerOnLeftSide winnerOnLeftSide = voteService.getCountOfWinnerOnLeftAndRightSide();

        checkEntry(new WinnerOnLeftSide(0, 0), winnerOnLeftSide);

        verify(voteRepository, times(1)).getCountOfWInnerOnLeftAndRightSide();
    }

    @Test
    void getDurationStatistic() {

        when(voteRepository.findDuration()).thenReturn(Arrays.asList(500, 1200, 1501, 2500, 10000));

        DurationStatisticData durationStatisticData = voteService.getDurationStatistic();

        Map<Integer, Integer> durationStatisticDataExpected = new HashMap<>();
        durationStatisticDataExpected.put(1, 2);
        durationStatisticDataExpected.put(2, 1);
        durationStatisticDataExpected.put(3, 1);
        durationStatisticDataExpected.put(4, 0);
        durationStatisticDataExpected.put(5, 0);
        durationStatisticDataExpected.put(6, 0);
        durationStatisticDataExpected.put(7, 0);
        durationStatisticDataExpected.put(8, 0);
        durationStatisticDataExpected.put(9, 0);
        durationStatisticDataExpected.put(10, 1);
        checkEntry(new DurationStatisticData(durationStatisticDataExpected), durationStatisticData);

        verify(voteRepository, times(1)).findDuration();
    }

    private void checkEntry(Vote voteExpected, Vote voteActual) {

        assertEquals(voteExpected.getId(), voteActual.getId());
        assertEquals(voteExpected.getDatetime(), voteActual.getDatetime());
        assertEquals(voteExpected.getDuration(), voteActual.getDuration());
        assertEquals(voteExpected.getFk_ImageId_Loser(), voteActual.getFk_ImageId_Loser());
        assertEquals(voteExpected.getFk_ImageId_Winner(), voteActual.getFk_ImageId_Winner());
        assertEquals(voteExpected.isWinnerOnLeftSide(), voteActual.isWinnerOnLeftSide());
    }

    private void checkEntry(WinnerOnLeftSide winnerOnLeftSideExpected, WinnerOnLeftSide winnerOnLeftSideActual) {

        assertEquals(winnerOnLeftSideExpected.getWinnerOnLeftSideCount(), winnerOnLeftSideActual.getWinnerOnLeftSideCount());
        assertEquals(winnerOnLeftSideExpected.getWinnerOnRightSideCount(), winnerOnLeftSideActual.getWinnerOnRightSideCount());
    }

    private void checkEntry(DurationStatisticData durationStatisticDataExpected, DurationStatisticData durationStatisticDataActual) {

        assertEquals(durationStatisticDataExpected.getDurationData(), durationStatisticDataActual.getDurationData());
    }

    private void checkRankingObject(Ranking rankingExpected, Ranking rankingActual) {

        assertEquals(rankingExpected.getImage(), rankingActual.getImage());
        assertEquals(rankingExpected.getWins(), rankingActual.getWins());
        assertEquals(rankingExpected.getLoses(), rankingActual.getLoses());
        assertEquals(rankingExpected.getWinsPerVote(), rankingActual.getWinsPerVote());
        assertEquals(rankingExpected.getRank(), rankingActual.getRank());
    }
}