package com.softwareproject.backend.service;

import com.softwareproject.backend.api.Ranking;
import com.softwareproject.backend.api.RankingResponse;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoteServiceTest {

    private final VoteRepository voteRepository = mock(VoteRepository.class);
    private final VoteService voteService = new VoteService(voteRepository);

    private final List<Vote> voteListMock = new ArrayList<>();
    private Vote voteResponseAndRequest;
    private final Image imageWinner = new Image();
    private final Image imageLoser = new Image();
    private final Image imageOne = new Image(123, "abc",  "http://abc.de", 100, 200, 2, "cbd");
    private final Image imageTwo = new Image(123, "def",  "http://def.de", 100, 200, 2, "cbd");
    private final Image imageThree = new Image(123, "ghi",  "http://ghi.de", 100, 200, 2, "cbd");

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
    void getRanking(){

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
        checkRankingObject(new Ranking(imageOne, 3, 1, 0.75), rankingList.get(0));
        checkRankingObject(new Ranking(imageTwo, 2, 2, 0.5), rankingList.get(1));
        checkRankingObject(new Ranking(imageThree, 0, 5, 0), rankingList.get(2));

        verify(voteRepository, times(1)).getWins();
        verify(voteRepository, times(1)).getLoses();
    }

    private void checkEntry(Vote voteExpected, Vote voteActual) {

        assertEquals(voteExpected.getId(), voteActual.getId());
        assertEquals(voteExpected.getDatetime(), voteActual.getDatetime());
        assertEquals(voteExpected.getDuration(), voteActual.getDuration());
        assertEquals(voteExpected.getFk_ImageId_Loser(), voteActual.getFk_ImageId_Loser());
        assertEquals(voteExpected.getFk_ImageId_Winner(), voteActual.getFk_ImageId_Winner());
        assertEquals(voteExpected.isWinnerOnLeftSide(), voteActual.isWinnerOnLeftSide());
    }

    private void checkRankingObject(Ranking rankingExpected, Ranking rankingActual) {

        assertEquals(rankingExpected.getImage(), rankingActual.getImage());
        assertEquals(rankingExpected.getWins(), rankingActual.getWins());
        assertEquals(rankingExpected.getLoses(), rankingActual.getLoses());
        assertEquals(rankingExpected.getWinsPerVote(), rankingActual.getWinsPerVote());
    }
}