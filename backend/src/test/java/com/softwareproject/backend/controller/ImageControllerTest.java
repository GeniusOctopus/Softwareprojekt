package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.model.Vote;
import com.softwareproject.backend.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
class ImageControllerTest {

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mockMvc;

    private final List<Image> imageList = new ArrayList<>();
    private final Image imageOne = new Image(1, 12345, "abc", "http://abc.de", 100, 200, 1);
    private final Image imageTwo = new Image(2, 123455, "gdewrf", "http://werwer.de", 450, 120, 2);

    @BeforeEach
    void SetUp(){

        imageList.add(imageOne);
        imageList.add(imageTwo);
    }

    @Test
    void getAllImages() throws Exception {

        String jsonResponse = "[{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1},{\"id\":2,\"datetime\":123455,\"catApiId\":\"gdewrf\",\"url\":\"http://werwer.de\",\"width\":450,\"height\":120,\"timesShown\":2}]";

        when(imageService.getAllImages()).thenReturn(imageList);

        mockMvc.perform(get("/image/images"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(imageService, times(1)).getAllImages();
    }

    @Test
    void getImagesForVoting() throws Exception {

        String jsonResponse = "[{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1},{\"id\":2,\"datetime\":123455,\"catApiId\":\"gdewrf\",\"url\":\"http://werwer.de\",\"width\":450,\"height\":120,\"timesShown\":2}]";

        when(imageService.getImagesForVoting()).thenReturn(imageList);

        mockMvc.perform(get("/image/voting"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(imageService, times(1)).getImagesForVoting();
    }

    @Test
    void increaseTimesShownForVoting() throws Exception {

        String jsonRequest = "{\"id\":1}";

        mockMvc.perform(put("/image/increaseTimesShownForVoting/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(imageService, times(1)).increaseTimesShownForVoting(1);
    }

    @Test
    void addImage() throws Exception {

        String jsonRequestAndResponse = "{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1}";

        when(imageService.addImage(imageOne)).thenReturn(imageOne);

        mockMvc.perform(post("/image/add")
                        .content(jsonRequestAndResponse)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonRequestAndResponse));

        verify(imageService, times(1)).addImage(imageOne);
    }

    @Test
    void addImages() throws Exception {

        String jsonResponse = "[{\"id\":1,\"datetime\":12345,\"catApiId\":\"abc\",\"url\":\"http://abc.de\",\"width\":100,\"height\":200,\"timesShown\":1},{\"id\":2,\"datetime\":123455,\"catApiId\":\"gdewrf\",\"url\":\"http://werwer.de\",\"width\":450,\"height\":120,\"timesShown\":2}]";

        when(imageService.addImages(100, 1.4, 1.6)).thenReturn(imageList);

        mockMvc.perform(get("/image/addMultiple/100")
                        .queryParam("minProportionFactor", "1.4")
                        .queryParam("maxProportionFactor", "1.6"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(imageService, times(1)).addImages(100, 1.4, 1.6);
    }
}