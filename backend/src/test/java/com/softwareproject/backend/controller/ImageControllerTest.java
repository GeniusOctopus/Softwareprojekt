package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
class ImageControllerTest {

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mockMvc;

    private final List<Image> imageList = new ArrayList<>();

    @BeforeEach
    void SetUp(){

        imageList.add(new Image(1, 12345, "gdf", "http://abc.de", 50, 120, 4));
        imageList.add(new Image(2, 123455, "gdewrf", "http://werwer.de", 450, 120, 2));
    }

    @Test
    void getAllImages() throws Exception {

        String jsonResponse = "[{\"id\":1,\"datetime\":12345,\"catApiId\":\"gdf\",\"url\":\"http://abc.de\",\"width\":50,\"height\":120,\"timesShown\":4},{\"id\":2,\"datetime\":123455,\"catApiId\":\"gdewrf\",\"url\":\"http://werwer.de\",\"width\":450,\"height\":120,\"timesShown\":2}]";

        when(imageService.getAllImages()).thenReturn(imageList);

        mockMvc.perform(get("/image/images"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(imageService, times(1)).getAllImages();
    }

    @Test
    void getImagesForVoting() throws Exception {

        String jsonResponse = "[{\"id\":1,\"datetime\":12345,\"catApiId\":\"gdf\",\"url\":\"http://abc.de\",\"width\":50,\"height\":120,\"timesShown\":4},{\"id\":2,\"datetime\":123455,\"catApiId\":\"gdewrf\",\"url\":\"http://werwer.de\",\"width\":450,\"height\":120,\"timesShown\":2}]";

        when(imageService.getImagesForVoting()).thenReturn(imageList);

        mockMvc.perform(get("/image/voting"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(imageService, times(1)).getImagesForVoting();
    }

    @Test
    void increaseTimesShownForVoting() throws Exception {

        /*
        String jsonRequest = "\"id\":1";

        doNothing().when(imageService.getImagesForVoting());

        mockMvc.perform(put("/image/image/increaseTimesShownForVoting/1").content(jsonRequest))
                .andExpect(status().isOk());

        verify(imageService, times(1)).increaseTimesShownForVoting(1);

         */
    }

    @Test
    void addImage() throws Exception {
    }

    @Test
    void addImages() throws Exception {
    }
}