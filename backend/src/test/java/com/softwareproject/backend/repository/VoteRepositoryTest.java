package com.softwareproject.backend.repository;

import com.softwareproject.backend.BackendApplication;
import com.softwareproject.backend.response.RankingResponse;
import com.softwareproject.backend.response.WinnerOnLeftAndRightSideResponse;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.model.Vote;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
    private final Vote voteTwo = new Vote(2, 123, imageTwo, imageOne, 11, false);
    private final Vote voteThree = new Vote(3, 1234, imageOne, imageTwo, 12, true);

    @Test
    void getWins() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<RankingResponse> rankingResponseList = voteRepository.getWins();

        assertEquals(2, rankingResponseList.size());
        checkEntry(new RankingResponse(imageOne, 1648112468722L, 2), rankingResponseList.get(0));
        checkEntry(new RankingResponse(imageTwo, 1648112468722L, 1), rankingResponseList.get(1));
    }

    @Test
    void getLoses() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<RankingResponse> rankingResponseList = voteRepository.getLoses();

        assertEquals(2, rankingResponseList.size());
        checkEntry(new RankingResponse(imageOne, 1648112468722L, 1), rankingResponseList.get(0));
        checkEntry(new RankingResponse(imageTwo, 1648112468722L, 2), rankingResponseList.get(1));
    }

    @Test
    void getCountOfWInnerOnLeftAndRightSide() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<WinnerOnLeftAndRightSideResponse> winnerOnLeftAndRightSideResponseList = voteRepository.getCountOfWInnerOnLeftAndRightSide();

        assertEquals(2, winnerOnLeftAndRightSideResponseList.size());
        checkEntry(new WinnerOnLeftAndRightSideResponse(false, 2), winnerOnLeftAndRightSideResponseList.get(0));
        checkEntry(new WinnerOnLeftAndRightSideResponse(true, 1), winnerOnLeftAndRightSideResponseList.get(1));
    }

    @Test
    void findDatetime() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<Long> datetimeList = voteRepository.findDatetime();

        assertEquals(3, datetimeList.size());
        assertThat(datetimeList, containsInAnyOrder(12L, 123L, 1234L));
    }

    @Test
    void findDuration() {

        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo));
        voteRepository.saveAll(Arrays.asList(voteOne, voteTwo, voteThree));

        List<Integer> durationList = voteRepository.findDuration();

        assertEquals(3, durationList.size());
        assertThat(durationList, containsInAnyOrder(10, 11, 12));
    }

    private void checkEntry(RankingResponse rankingResponseExpected, RankingResponse rankingResponseActual) {

        assertEquals(rankingResponseExpected.getImage(), rankingResponseActual.getImage());
        assertEquals(rankingResponseExpected.getValue(), rankingResponseActual.getValue());
        assertEquals(rankingResponseExpected.getDatetime(), rankingResponseActual.getDatetime());
    }

    private void checkEntry(WinnerOnLeftAndRightSideResponse winnerOnLeftAndRightSideResponseExpected, WinnerOnLeftAndRightSideResponse winnerOnLeftAndRightSideResponseActual) {

        assertEquals(winnerOnLeftAndRightSideResponseExpected.isWinnerOnLeftSide(), winnerOnLeftAndRightSideResponseActual.isWinnerOnLeftSide());
        assertEquals(winnerOnLeftAndRightSideResponseExpected.getCount(), winnerOnLeftAndRightSideResponseActual.getCount());
    }
}