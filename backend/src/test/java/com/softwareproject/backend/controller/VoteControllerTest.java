package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Ranking;
import com.softwareproject.backend.model.WinnerOnLeftSide;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @MockBean
    private VoteService voteService;

    @Autowired
    private MockMvc mockMvc;

    private final Image imageOne = new Image(1, 12345, "abc", "http://abc.de", 100, 200, 1, "abc");
    private final Image imageTwo = new Image(2, 123456, "ght", "http://ght.de", 150, 300, 2, "abcd");

    @Test
    void getAllIVotes() throws Exception {

        List<Vote> voteList = new ArrayList<>();
        voteList.add(new Vote(
                1, 123456789,
                new Image(1, 12345, "abc", "http://abc.de", 100, 200, 1, "abc"),
                new Image(2, 123456, "ght", "http://ght.de", 150, 300, 2, "abc"),
                10,
                false));
        voteList.add(new Vote(
                2, 123456789,
                new Image(3, 12345, "gdf", "http://abc.de", 50, 120, 4, "abc"),
                new Image(4, 123456, "fsd", "http://ght.de", 150, 300, 3, "abc"),
                10,
                false));

        String jsonResponse = "[{\"id\":1,\"datetime\":123456789,\"fk_ImageId_Winner\":{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1},\"fk_ImageId_Loser\":{\"id\":2,\"datetime\":123456,\"catApiId\":\"ght\",\"url\":\"http://ght.de\",\"width\":150,\"height\":300,\"timesShown\":2},\"duration\":10,\"winnerOnLeftSide\":false},{\"id\":2,\"datetime\":123456789,\"fk_ImageId_Winner\":{\"id\":3,\"datetime\":12345,\"catApiId\":\"gdf\",\"url\":\"http://abc.de\",\"width\":50,\"height\":120,\"timesShown\":4},\"fk_ImageId_Loser\":{\"id\":4,\"datetime\":123456,\"catApiId\":\"fsd\",\"url\":\"http://ght.de\",\"width\":150,\"height\":300,\"timesShown\":3},\"duration\":10,\"winnerOnLeftSide\":false}]";

        when(voteService.getAllIVotes()).thenReturn(voteList);

        mockMvc.perform(get("/vote/votes"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(voteService, times(1)).getAllIVotes();
    }

    @Test
    void addVote() throws Exception {

        Vote vote = new Vote(1, 123456789, imageOne, imageTwo, 10, false);

        String jsonRequestAndResponse = "{\"id\":1,\"datetime\":123456789,\"fk_ImageId_Winner\":{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1,\"catApiBreedId\":\"abc\"},\"fk_ImageId_Loser\":{\"id\":2,\"datetime\":123456,\"catApiId\":\"ght\",\"url\":\"http://ght.de\",\"width\":150,\"height\":300,\"timesShown\":2,\"catApiBreedId\":\"abcd\"},\"duration\":10,\"winnerOnLeftSide\":false}";

        when(voteService.addVote(vote)).thenReturn(vote);

        mockMvc.perform(post("/vote/add")
                        .content(jsonRequestAndResponse)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRequestAndResponse));

        verify(voteService, times(1)).addVote(vote);
    }

    @Test
    void getRanking() throws Exception {

        List<Ranking> rankingList = new ArrayList<>();
        rankingList.add(new Ranking(imageOne, 123456789, 1, 1, 0.5));
        rankingList.add(new Ranking(imageTwo, 123456789, 2, 2, 0.5));

        String jsonResponse = "[{\"image\":{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1,\"catApiBreedId\":\"abc\"},\"datetime\":123456789,\"wins\":1,\"loses\":1,\"winsPerVote\":0.5},{\"image\":{\"id\":2,\"datetime\":123456,\"catApiId\":\"ght\",\"url\":\"http://ght.de\",\"width\":150,\"height\":300,\"timesShown\":2,\"catApiBreedId\":\"abcd\"},\"datetime\":123456789,\"wins\":2,\"loses\":2,\"winsPerVote\":0.5}]";

        when(voteService.getRanking()).thenReturn(rankingList);

        mockMvc.perform(get("/vote/ranking"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(voteService, times(1)).getRanking();
    }

    @Test
    void getCountOfWinnerOnLeftAndRightSide() throws Exception {

        WinnerOnLeftSide winnerOnLeftSide = new WinnerOnLeftSide(3, 8);

        String jsonResponse = "{\"winnerOnLeftSideCount\":3,\"winnerOnRightSideCount\":8}";

        when(voteService.getCountOfWinnerOnLeftAndRightSide()).thenReturn(winnerOnLeftSide);

        mockMvc.perform(get("/vote/statistics/countOfWinnerOnLeftAndRightSide"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(voteService, times(1)).getCountOfWinnerOnLeftAndRightSide();
    }
}