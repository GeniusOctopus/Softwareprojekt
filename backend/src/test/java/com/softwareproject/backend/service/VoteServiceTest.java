package com.softwareproject.backend.service;

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
    private Vote voteRequest;
    private final Image imageWinner = new Image();
    private final Image imageLoser = new Image();

    @BeforeEach
    void setUp() {

        voteRequest = new Vote(123, 123456789, imageWinner, imageLoser, 10, true);
        voteListMock.add(voteRequest);
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

        when(voteRepository.save(voteRequest)).thenReturn(voteRequest);

        Vote voteResponse = voteService.addVote(voteRequest);
        checkEntry(voteRequest, voteResponse);

        verify(voteRepository, times(1)).save(voteRequest);
    }

    private void checkEntry(Vote voteExpected, Vote voteActual) {

        assertEquals(voteExpected.getId(), voteActual.getId());
        assertEquals(voteExpected.getDatetime(), voteActual.getDatetime());
        assertEquals(voteExpected.getDuration(), voteActual.getDuration());
        assertEquals(voteExpected.getFk_ImageId_Loser(), voteActual.getFk_ImageId_Loser());
        assertEquals(voteExpected.getFk_ImageId_Winner(), voteActual.getFk_ImageId_Winner());
        assertEquals(voteExpected.isWinnerOnLeftSide(), voteActual.isWinnerOnLeftSide());
    }
}