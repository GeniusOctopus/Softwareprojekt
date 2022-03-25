package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.service.VoteService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getAllIVotes() throws Exception {

        List<Vote> voteList = new ArrayList<>();
        voteList.add(new Vote(
                1, 123456789,
                new Image(1, 12345, "abc", "http://abc.de", 100, 200, 1),
                new Image(2, 123456, "ght", "http://ght.de", 150, 300, 2),
                10,
                false));
        voteList.add(new Vote(
                2, 123456789,
                new Image(3, 12345, "gdf", "http://abc.de", 50, 120, 4),
                new Image(4, 123456, "fsd", "http://ght.de", 150, 300, 3),
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

        Vote vote = new Vote(
                1, 123456789,
                new Image(1, 12345, "abc", "http://abc.de", 100, 200, 1),
                new Image(2, 123456, "ght", "http://ght.de", 150, 300, 2),
                10,
                false);

        String jsonResponse = "{\"id\":1,\"datetime\":123456789,\"fk_ImageId_Winner\":{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1},\"fk_ImageId_Loser\":{\"id\":2,\"datetime\":123456,\"catApiId\":\"ght\",\"url\":\"http://ght.de\",\"width\":150,\"height\":300,\"timesShown\":2},\"duration\":10,\"winnerOnLeftSide\":false}";
        String jsonRequest = "{\n" +
                "  \"id\": 1,\n" +
                "  \"datetime\": 123456789,\n" +
                "  \"fk_ImageId_Winner\": {\n" +
                "    \"id\": 1,\n" +
                "    \"datetime\": 12345,\n" +
                "    \"catApiId\": \"abc\",\n" +
                "    \"url\": \"http://abc.de\",\n" +
                "    \"width\": 100,\n" +
                "    \"height\": 200,\n" +
                "    \"timesShown\": 1\n" +
                "  },\n" +
                "  \"fk_ImageId_Loser\": {\n" +
                "    \"id\": 2,\n" +
                "    \"datetime\": 123456,\n" +
                "    \"catApiId\": \"ght\",\n" +
                "    \"url\": \"http://ght.de\",\n" +
                "    \"width\": 150,\n" +
                "    \"height\": 300,\n" +
                "    \"timesShown\": 2\n" +
                "  },\n" +
                "  \"duration\": 10,\n" +
                "  \"winnerOnLeftSide\": false\n" +
                "}";

        when(voteService.addVote(vote)).thenReturn(vote);

        mockMvc.perform(post("/vote/add")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(voteService, times(1)).addVote(vote);
    }
}