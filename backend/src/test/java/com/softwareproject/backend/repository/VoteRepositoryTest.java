package com.softwareproject.backend.repository;

import com.softwareproject.backend.BackendApplication;
import com.softwareproject.backend.api.RankingResponse;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.model.Vote;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
class VoteRepositoryTest {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ImageRepository imageRepository;

    private final Image imageOne = new Image(1, 1648112468722L, "bkm", "https://cdn2.thecatapi.com/images/bkm.jpg", 669, 650, 0, "abc");
    private final Image imageTwo = new Image(2, 1648112468722L, "cke", "https://cdn2.thecatapi.com/images/cke.jpg", 620, 465, 0, "abc");
    private final Vote voteOne = new Vote(1, 12, imageOne, imageTwo, 10, false);
    private final Vote voteTwo = new Vote(2, 123, imageTwo, imageOne, 10, false);
    private final Vote voteThree = new Vote(3, 1234, imageOne, imageTwo, 10, true);

    @Test
    void getWins() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<RankingResponse> rankingResponseList = voteRepository.getWins();

        assertEquals(2, rankingResponseList.size());
        checkEntry(new RankingResponse("https://cdn2.thecatapi.com/images/bkm.jpg", 1648112468722L, 2), rankingResponseList.get(0));
        checkEntry(new RankingResponse("https://cdn2.thecatapi.com/images/cke.jpg", 1648112468722L, 1), rankingResponseList.get(1));
    }

    @Test
    void getLoses() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<RankingResponse> rankingResponseList = voteRepository.getLoses();

        assertEquals(2, rankingResponseList.size());
        checkEntry(new RankingResponse("https://cdn2.thecatapi.com/images/bkm.jpg", 1648112468722L, 1), rankingResponseList.get(0));
        checkEntry(new RankingResponse("https://cdn2.thecatapi.com/images/cke.jpg", 1648112468722L, 2), rankingResponseList.get(1));
    }

    private void checkEntry(RankingResponse rankingResponseExpected, RankingResponse rankingResponseActual) {

        assertEquals(rankingResponseExpected.getUrl(), rankingResponseActual.getUrl());
        assertEquals(rankingResponseExpected.getValue(), rankingResponseActual.getValue());
        assertEquals(rankingResponseExpected.getDatetime(), rankingResponseActual.getDatetime());
    }
}