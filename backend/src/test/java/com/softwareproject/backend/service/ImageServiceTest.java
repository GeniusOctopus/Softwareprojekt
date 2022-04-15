package com.softwareproject.backend.service;

import com.softwareproject.backend.model.ImageDetails;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.repository.ImageRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    private final ImageRepository imageRepository = mock(ImageRepository.class);
    private final ImageService imageService = new ImageService(imageRepository);

    private static final Image IMAGE_ONE = new Image(1, 123456789L, "nN4B8YFbb", "http://BildZurKatze.png", 1200, 720, 0, "somi");
    private static final Image IMAGE_TWO = new Image(2, 1234569L, "catApiId2", "http://BildZurKatze2.png", 1201, 721, 1, "abc");
    private static final Image IMAGE_THREE = new Image(3, 123456L, "catApiId3", "http://BildZurKatze3.png", 1202, 722, 1, "abc");

    @Test
    void getAllImages() {

        List<Image> imageListMock = new ArrayList<>();
        imageListMock.add(IMAGE_ONE);

        when(imageRepository.findAll()).thenReturn(imageListMock);

        List<Image> imageListResponse = imageService.getAllImages();

        assertEquals(1, imageListResponse.size());
        checkEntry(IMAGE_ONE, imageListResponse.get(0));

        verify(imageRepository, times(1)).findAll();
    }

    @Test
    void getImagesForVoting() {

        List<Image> imageListMock = new ArrayList<>();
        imageListMock.add(IMAGE_ONE);
        imageListMock.add(IMAGE_TWO);
        imageListMock.add(IMAGE_THREE);

        when(imageRepository.getImagesForVoting()).thenReturn(imageListMock);

        List<Image> imageListResponse = imageService.getImagesForVoting();

        assertEquals(2, imageListResponse.size());
        checkEntry(IMAGE_ONE, imageListResponse.get(0));
        assertTrue(imageListResponse.get(1) == IMAGE_TWO || imageListResponse.get(1) == IMAGE_THREE);

        verify(imageRepository, times(1)).getImagesForVoting();
    }

    @Test
    void getImagesForVotingAllImagesHaveSameTimesOfShown() {

        List<Image> imageListMock = new ArrayList<>();
        imageListMock.add(IMAGE_TWO);
        imageListMock.add(IMAGE_THREE);

        when(imageRepository.getImagesForVoting()).thenReturn(imageListMock);

        List<Image> imageListResponse = imageService.getImagesForVoting();

        assertEquals(2, imageListResponse.size());
        if (imageListResponse.get(0).getId() == IMAGE_TWO.getId()){
            checkEntry(IMAGE_TWO, imageListResponse.get(0));
            checkEntry(IMAGE_THREE, imageListResponse.get(1));
        }else {
            checkEntry(IMAGE_TWO, imageListResponse.get(1));
            checkEntry(IMAGE_THREE, imageListResponse.get(0));
        }

        verify(imageRepository, times(1)).getImagesForVoting();
    }

    @Test
    void increaseTimesShownForVoting() {

        imageService.increaseTimesShownForVoting(123);

        verify(imageRepository, times(1)).increaseTimesShownForVoting(123);
    }

    @Test
    void addImage() {

        when(imageRepository.save(IMAGE_ONE)).thenReturn(IMAGE_ONE);

        Image imageResponse = imageService.addImage(IMAGE_ONE);

        checkEntry(IMAGE_ONE, imageResponse);

        verify(imageRepository, times(1)).save(IMAGE_ONE);
    }

    @Test
    void getImageDetailsById() {

        Optional<Image> imageOptional = Optional.of(IMAGE_ONE);
        when(imageRepository.findById(IMAGE_ONE.getId())).thenReturn(imageOptional);

        ImageDetails imageDetailsResponse = imageService.getImageDetailsById(IMAGE_ONE.getId());

        checkEntry(new ImageDetails(IMAGE_ONE, "Somali", "The Somali lives life to the fullest. He climbs higher, jumps farther, plays harder. Nothing escapes the notice of this highly intelligent and inquisitive cat. Somalis love the company of humans and other animals.", "Somalia", "https://en.wikipedia.org/wiki/Somali_(cat)"), imageDetailsResponse);

        verify(imageRepository, times(1)).findById(IMAGE_ONE.getId());
    }

    @Test
    void getImageDetailsByIdImageNotFoundInDatabase() {

        when(imageRepository.findById(IMAGE_ONE.getId())).thenReturn(Optional.empty());

        ImageDetails imageDetailsResponse = imageService.getImageDetailsById(IMAGE_ONE.getId());

        checkEntry(new ImageDetails(), imageDetailsResponse);

        verify(imageRepository, times(1)).findById(IMAGE_ONE.getId());
    }

    private void checkEntry(Image imageExpected, Image imageActual) {

        assertEquals(imageExpected.getId(), imageActual.getId());
        assertEquals(imageExpected.getDatetime(), imageActual.getDatetime());
        assertEquals(imageExpected.getCatApiId(), imageActual.getCatApiId());
        assertEquals(imageExpected.getUrl(), imageActual.getUrl());
        assertEquals(imageExpected.getWidth(), imageActual.getWidth());
        assertEquals(imageExpected.getHeight(), imageActual.getHeight());
        assertEquals(imageExpected.getTimesShown(), imageActual.getTimesShown());
        assertEquals(imageExpected.getCatApiBreedId(), imageActual.getCatApiBreedId());
    }

    private void checkEntry(ImageDetails imageDetailsExpected, ImageDetails imageDetailsActual) {

        assertEquals(imageDetailsExpected.getImage(), imageDetailsActual.getImage());
        assertEquals(imageDetailsExpected.getBreedName(), imageDetailsActual.getBreedName());
        assertEquals(imageDetailsExpected.getDescription(), imageDetailsActual.getDescription());
        assertEquals(imageDetailsExpected.getOrigin(), imageDetailsActual.getOrigin());
        assertEquals(imageDetailsExpected.getWikipediaUrl(), imageDetailsActual.getWikipediaUrl());
    }
}