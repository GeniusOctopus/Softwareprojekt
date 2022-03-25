package com.softwareproject.backend.service;

import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.repository.ImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    private final ImageRepository imageRepository = mock(ImageRepository.class);
    private final ImageService imageService = new ImageService(imageRepository);

    @Test
    void getAllImages() {

        List<Image> imageListMock = new ArrayList<>();
        imageListMock.add(new Image(1, 123456789L, "catApiId", "http://BildZurKatze.png", 1200, 720, 0));

        when(imageRepository.findAll()).thenReturn(imageListMock);

        List<Image> imageListResponse = imageService.getAllImages();

        assertEquals(1, imageListResponse.size());
        assertEquals(1, imageListResponse.get(0).getId());
        assertEquals(123456789L, imageListResponse.get(0).getDatetime());
        assertEquals("catApiId", imageListResponse.get(0).getCatApiId());
        assertEquals("http://BildZurKatze.png", imageListResponse.get(0).getUrl());
        assertEquals(1200, imageListResponse.get(0).getWidth());
        assertEquals(720, imageListResponse.get(0).getHeight());
        assertEquals(0, imageListResponse.get(0).getTimesShown());

        verify(imageRepository, times(1)).findAll();
    }

    @Test
    void getImagesForVoting() {

        List<Image> imageListMock = new ArrayList<>();
        imageListMock.add(new Image(1, 123456789L, "catApiId1", "http://BildZurKatze1.png", 1200, 720, 0));
        imageListMock.add(new Image(2, 123456789L, "catApiId2", "http://BildZurKatze2.png", 1201, 721, 1));

        when(imageRepository.getImagesForVoting()).thenReturn(imageListMock);

        List<Image> imageListResponse = imageService.getImagesForVoting();

        assertEquals(2, imageListResponse.size());
        assertEquals(1, imageListResponse.get(0).getId());
        assertEquals(123456789L, imageListResponse.get(0).getDatetime());
        assertEquals("catApiId1", imageListResponse.get(0).getCatApiId());
        assertEquals("http://BildZurKatze1.png", imageListResponse.get(0).getUrl());
        assertEquals(1200, imageListResponse.get(0).getWidth());
        assertEquals(720, imageListResponse.get(0).getHeight());
        assertEquals(0, imageListResponse.get(0).getTimesShown());
        assertEquals(2, imageListResponse.get(1).getId());
        assertEquals(123456789L, imageListResponse.get(1).getDatetime());
        assertEquals("catApiId2", imageListResponse.get(1).getCatApiId());
        assertEquals("http://BildZurKatze2.png", imageListResponse.get(1).getUrl());
        assertEquals(1201, imageListResponse.get(1).getWidth());
        assertEquals(721, imageListResponse.get(1).getHeight());
        assertEquals(1, imageListResponse.get(1).getTimesShown());

        verify(imageRepository, times(1)).getImagesForVoting();
    }

    @Test
    void increaseTimesShownForVoting() {

        imageService.increaseTimesShownForVoting(123);

        verify(imageRepository, times(1)).increaseTimesShownForVoting(123);
    }

    @Test
    @DisplayName("Speichert ein Bild in der Datenbank")
    void addImage() {

        Image image = new Image(1, 123456789L, "catApiId", "http://BildZurKatze.png", 1200, 720, 0);

        when(imageRepository.save(image)).thenReturn(image);

        Image imageResponse = imageService.addImage(image);

        assertEquals(1, imageResponse.getId());
        assertEquals(123456789L, imageResponse.getDatetime());
        assertEquals("catApiId", imageResponse.getCatApiId());
        assertEquals("http://BildZurKatze.png", imageResponse.getUrl());
        assertEquals(1200, imageResponse.getWidth());
        assertEquals(720, imageResponse.getHeight());
        assertEquals(0, imageResponse.getTimesShown());

        verify(imageRepository, times(1)).save(image);
    }
}